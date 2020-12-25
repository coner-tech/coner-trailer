package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.cooccurring
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.path
import org.coner.crispyfish.model.Registration
import org.coner.trailer.Event
import org.coner.trailer.Participant
import org.coner.trailer.Person
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.util.clikt.toLocalDate
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.CrispyFishRegistrationView
import org.coner.trailer.cli.view.EventView
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishPersonMapper
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.coner.trailer.io.service.PersonService
import org.coner.trailer.io.verification.EventCrispyFishForcePersonVerification
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.nio.file.Path
import java.time.LocalDate
import java.util.*
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.extension

@ExperimentalPathApi
class EventAddCommand(
    di: DI
) : CliktCommand(
    name = "add",
    help = "Add an Event"
), DIAware {

    override val di: DI by findOrSetObject { di }

    private val dbConfig: DatabaseConfiguration by instance()
    private val service: EventService by instance()
    private val view: EventView by instance()
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService by instance()
    private val personService: PersonService by instance()
    private val crispyFishRegistrationView: CrispyFishRegistrationView by instance()
    private val crispyFishVerification: EventCrispyFishForcePersonVerification by instance()
    private val crispyFishParticipantMapper: CrispyFishParticipantMapper by instance()
    private val crispyFishPersonMapper: CrispyFishPersonMapper by instance()

    private val id: UUID by option(hidden = true)
        .convert { toUuid(it) }
        .prompt(default = "${UUID.randomUUID()}")
    private val name: String by option()
        .prompt()
    private val date: LocalDate by option()
        .convert { toLocalDate(it) }
        .prompt()

    class CrispyFishOptions : OptionGroup() {

        val eventControlFile: Path by option("--crispy-fish-event-control-file")
            .path(
                mustExist = true,
                canBeFile = true,
                canBeDir = false,
                canBeSymlink = false,
                mustBeReadable = true
            )
            .required()
            .validate {
                if (it.extension != "ecf") {
                    fail("Must be a .ecf file")
                }
            }
        val classDefinitionFile: Path by option("--crispy-fish-class-definition-file")
            .path(
                mustExist = true,
                canBeFile = true,
                canBeDir = false,
                canBeSymlink = false,
                mustBeReadable = true
            )
            .required()
            .validate {
                if (it.extension != "def") {
                    fail("Must be a .def file")
                }
            }
    }
    private val crispyFishOptions: CrispyFishOptions? by CrispyFishOptions().cooccurring()

    override fun run() {
        crispyFishOptions?.also {
            if (!it.eventControlFile.startsWith(dbConfig.crispyFishDatabase)) {
                echo("Event Control File must be within the crispy fish database")
                throw Abort()
            }
            if (!it.classDefinitionFile.startsWith(dbConfig.crispyFishDatabase)) {
                echo("Class Definition File must be within the crispy fish database")
                throw Abort()
            }
        }
        val crispyFishPair = crispyFishOptions?.let { options ->
            val context = crispyFishEventMappingContextService.load(
                    eventControlFilePath = options.eventControlFile,
                    classDefinitionFilePath = options.classDefinitionFile
                )
            val crispyFish = assembleCrispyFish(
                options = options,
                context = context
            )
            crispyFish to context
        }
        val create = Event(
            id = id,
            name = name,
            date = date,
            crispyFish = crispyFishPair?.first
        )
        service.create(
            create = create,
            context = crispyFishPair?.second,
            eventCrispyFishForcePersonVerificationFailureCallback = object : EventCrispyFishForcePersonVerification.FailureCallback {
                override fun onRegistrationWithoutClubMemberId(registration: Registration) {
                    fail(registration)
                }

                override fun onPersonWithClubMemberIdNotFound(registration: Registration) {
                    fail(registration)
                }

                override fun onMultiplePeopleWithClubMemberIdFound(registration: Registration) {
                    fail(registration)
                }

                private fun fail(registration: Registration) {
                    echo("Verification failed on registration:")
                    echo(crispyFishRegistrationView.render(registration))
                    throw Abort()
                }
            }
        )
        echo(view.render(create))
    }

    private fun assembleCrispyFish(
        options: CrispyFishOptions,
        context: CrispyFishEventMappingContext
    ): Event.CrispyFishMetadata {
        val initial = Event.CrispyFishMetadata(
            eventControlFile = dbConfig.crispyFishDatabase.relativize(options.eventControlFile).toString(),
            classDefinitionFile = dbConfig.crispyFishDatabase.relativize(options.classDefinitionFile).toString(),
            forcePeople = emptyMap()
        )
        val forcePeople = mutableMapOf<Participant.Signage, Person>()
        crispyFishVerification.verifyRegistrations(context, initial.forcePeople, object  : EventCrispyFishForcePersonVerification.FailureCallback {

            override fun onRegistrationWithoutClubMemberId(registration: Registration) {
                echo("Found registration without club member ID")
                echo(crispyFishRegistrationView.render(registration))
                val suggestions = personService.searchByNameFrom(registration)
                handleAmbiguousRegistration(registration, suggestions)
            }

            override fun onPersonWithClubMemberIdNotFound(registration: Registration) {
                echo("Found registration with club member ID that didn't match any people")
                echo(crispyFishRegistrationView.render(registration))
                val suggestions = personService.searchByNameFrom(registration)
                handleAmbiguousRegistration(registration, suggestions)
            }

            override fun onMultiplePeopleWithClubMemberIdFound(registration: Registration) {
                echo("Found registration with club member ID that matched multiple people")
                echo(crispyFishRegistrationView.render(registration))
                val suggestions = personService.searchByClubMemberIdFrom(registration)
                handleAmbiguousRegistration(registration, suggestions)
            }

            private fun handleAmbiguousRegistration(
                registration: Registration,
                suggestions: List<Person>
            ) {
                val suggestionsOutput = suggestions.mapIndexed { index, person ->
                    "$index: ${person.firstName} ${person.lastName} (${person.id})"
                }.joinToString(separator = currentContext.console.lineSeparator)
                val providePersonId = suggestions.size + 1
                val createNewPerson = suggestions.size + 3
                val abort = suggestions.size + 4
                val person = requireNotNull(prompt(
                    text = """
                    ${if (suggestionsOutput.isNotEmpty()) suggestionsOutput else "No suggestions"}    
                    $providePersonId: Provide person ID
                    $createNewPerson: Create new person from registration
                    $abort: Abort
                    """.trimIndent(),
                    promptSuffix = "",
                    convert = { input ->
                        val decision = input.toIntOrNull()
                        when {
                            suggestions.isNotEmpty()
                                    && decision != null
                                    && decision in suggestions.indices -> suggestions[decision]
                            decision == providePersonId -> promptPersonId()
                            decision == createNewPerson -> createNewPerson(registration)
                            decision == abort -> throw Abort()
                            else -> throw UsageError("Not a valid choice: $input")
                        }
                    }
                )) { "Failed to convert input to person" }
                val signage = crispyFishParticipantMapper.toCoreSignage(context, registration)
                forcePeople[signage] = person
            }

            private fun promptPersonId(): Person {
                return checkNotNull(prompt("Person ID") { input ->
                    val personId = runCatching { UUID.fromString(input) }.getOrNull()
                        ?: throw UsageError("Invalid person ID format: $input")
                    runCatching { personService.findById(personId) }.getOrNull()
                        ?: throw UsageError("No person found with ID: $input")
                }) { "Failed to convert input to person" }
            }
            
            private fun createNewPerson(registration: Registration): Person {
                val motorsportRegMemberId = prompt("MotorsportReg Member ID")
                val person = crispyFishPersonMapper.toCore(registration, motorsportRegMemberId)
                personService.create(person)
                return person
            }
        })
        return initial.copy(
            forcePeople = forcePeople
        )
    }

}