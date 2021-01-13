package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import org.coner.crispyfish.model.Registration
import org.coner.trailer.Event
import org.coner.trailer.Participant
import org.coner.trailer.Person
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.CrispyFishRegistrationView
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.datasource.crispyfish.CrispyFishPersonMapper
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.coner.trailer.io.service.PersonService
import org.coner.trailer.io.verification.EventCrispyFishForcePersonVerification
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
class EventCrispyFishForcePersonAssembleCommand(
    di: DI
) : CliktCommand(
    name = "crispy-fish-force-person-assemble",
    help = "Interactively assemble the crispy fish force person map for an event"
), DIAware {

    override val di: DI by findOrSetObject { di }

    private val service: EventService by instance()
    private val personService: PersonService by instance()
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService by instance()
    private val crispyFishVerification: EventCrispyFishForcePersonVerification by instance()
    private val crispyFishRegistrationView: CrispyFishRegistrationView by instance()
    private val crispyFishParticipantMapper: CrispyFishParticipantMapper by instance()
    private val crispyFishPersonMapper: CrispyFishPersonMapper by instance()

    private val id: UUID by argument().convert { toUuid(it) }

    override fun run() {
        val event = service.findById(id)
        val crispyFish = event.crispyFish
        if (crispyFish == null) {
            echo("Selected Event lacks Crispy Fish Metadata")
            throw Abort()
        }
        val context = crispyFishEventMappingContextService.load(crispyFish)
        val forcePeople = mutableMapOf<Participant.Signage, Person>()
        crispyFishVerification.verifyRegistrations(context, crispyFish.forcePeople, object  : EventCrispyFishForcePersonVerification.FailureCallback {

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
                val providePersonId = suggestions.size
                val createNewPerson = suggestions.size + 1
                val abort = suggestions.size + 2
                val person = requireNotNull(prompt(
                    text = """
                    |${if (suggestionsOutput.isNotEmpty()) suggestionsOutput else "No suggestions"}
                    |$providePersonId: Provide person ID
                    |$createNewPerson: Create new person from registration
                    |$abort: Abort
                    |
                    """.trimMargin(),
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
                val person = crispyFishPersonMapper.toCore(
                    crispyFish = registration,
                    motorsportRegMemberId = motorsportRegMemberId
                )
                personService.create(person)
                return person
            }
        })
        val update = event.copy(
            crispyFish = crispyFish.copy(
                forcePeople = forcePeople
            )
        )
        service.update(
            update = update,
            context = context,
            eventCrispyFishForcePersonVerificationFailureCallback = null
        )
    }
}