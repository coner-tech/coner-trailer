package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import org.coner.crispyfish.model.Registration
import org.coner.trailer.Event
import org.coner.trailer.Person
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.CrispyFishRegistrationView
import org.coner.trailer.cli.view.PersonView
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.datasource.crispyfish.CrispyFishPersonMapper
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.coner.trailer.io.service.PersonService
import org.coner.trailer.io.verification.EventCrispyFishPersonMapVerifier
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
class EventCrispyFishPersonMapAssembleCommand(
    di: DI
) : CliktCommand(
    name = "crispy-fish-person-map-assemble",
    help = "Interactively assemble the Crispy Fish person map for an event"
), DIAware {

    override val di: DI by findOrSetObject { di }

    private val service: EventService by instance()
    private val personService: PersonService by instance()
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService by instance()
    private val eventCrispyFishPersonMapVerifier: EventCrispyFishPersonMapVerifier by instance()
    private val crispyFishRegistrationView: CrispyFishRegistrationView by instance()
    private val personView: PersonView by instance()
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
        val peopleMap = mutableMapOf<Event.CrispyFishMetadata.PeopleMapKey, Person>()
        eventCrispyFishPersonMapVerifier.verify(
            event = event,
            context = context,
            callback = object : EventCrispyFishPersonMapVerifier.Callback {

                override fun onMapped(
                    registration: Registration,
                    entry: Pair<Event.CrispyFishMetadata.PeopleMapKey, Person>
                ) {
                    enter()
                    echo("Found mapping. Reusing")
                    echo("Registration:")
                    echo(crispyFishRegistrationView.render(registration))
                    val (key, person) = entry
                    echo("Person:")
                    echo(personView.render(person))
                    peopleMap[key] = person
                    exit()
                }

                override fun onUnmappedMotorsportRegPersonExactMatch(
                    registration: Registration,
                    entry: Pair<Event.CrispyFishMetadata.PeopleMapKey, Person>
                ) {
                    enter()
                    echo("Found registration without mapping, but successfully cross-referenced to person via MSR assignment")
                    echo("Registration:")
                    echo(crispyFishRegistrationView.render(registration))
                    val (key, person) = entry
                    echo("Person:")
                    echo(personView.render(person))
                    peopleMap[key] = person
                    exit()
                }

                override fun onUnmappedClubMemberIdNull(registration: Registration) {
                    enter()
                    echo("Found registration without club member ID")
                    echo(crispyFishRegistrationView.render(registration))
                    val suggestions = personService.searchByNameFrom(registration)
                    handleAmbiguousRegistration(registration, suggestions)
                    exit()
                }

                override fun onUnmappedClubMemberIdNotFound(registration: Registration) {
                    enter()
                    echo("Found registration with club member ID that didn't match any people")
                    echo(crispyFishRegistrationView.render(registration))
                    val suggestions = personService.searchByNameFrom(registration)
                    handleAmbiguousRegistration(registration, suggestions)
                    exit()
                }

                override fun onUnmappedClubMemberIdAmbiguous(
                    registration: Registration,
                    peopleWithClubMemberId: List<Person>
                ) {
                    enter()
                    echo("Found registration with ambiguous club member ID")
                    echo(crispyFishRegistrationView.render(registration))
                    handleAmbiguousRegistration(registration, peopleWithClubMemberId)
                    exit()
                }

                override fun onUnmappedClubMemberIdMatchButNameMismatch(registration: Registration, person: Person) {
                    enter()
                    echo("Found registration with club member ID match but name mismatch")
                    echo(crispyFishRegistrationView.render(registration))
                    handleAmbiguousRegistration(registration, listOf(person))
                    exit()
                }

                override fun onUnmappedExactMatch(registration: Registration, person: Person) {
                    enter()
                    val key = Event.CrispyFishMetadata.PeopleMapKey(
                        signage = crispyFishParticipantMapper.toCoreSignage(
                            context = context,
                            crispyFish = registration
                        ),
                        firstName = registration.firstName,
                        lastName = registration.lastName
                    )
                    echo("Found unmapped registration with exact club member and name match.")
                    echo(crispyFishRegistrationView.render(registration))
                    echo("Auto-mapping to person:")
                    echo(personView.render(person))
                    peopleMap[key] = person
                    exit()
                }

                override fun onUnused(key: Event.CrispyFishMetadata.PeopleMapKey, person: Person) {
                    enter()
                    echo("Found unused mapping. Ignoring.")
                    echo("Signage: ${key.signage}")
                    echo("Name: ${key.firstName} ${key.lastName}")
                    echo("Person:")
                    echo(personView.render(person))
                    exit()
                }

                private fun enter() = echo(">>>")

                private fun exit() = echo("<<<")

                private fun handleAmbiguousRegistration(
                    registration: Registration,
                    suggestions: List<Person>
                ) {
                    echo("Specify force signage to person")
                    if (suggestions.isNotEmpty()) {
                        suggestions.forEachIndexed { index, person ->
                            echo("$index: ${person.firstName} ${person.lastName} (${person.id})")
                        }
                    } else {
                        echo("No people suggestions available. Have people been imported from membership records?")
                    }
                    val providePersonId = suggestions.size
                    echo("$providePersonId: Provide person ID")
                    val createNewPerson = suggestions.size + 1
                    echo("$createNewPerson: Create new person from registration")
                    val abort = suggestions.size + 2
                    echo("$abort: Abort")
                    val person = prompt(text = "", promptSuffix = "> ", convert = { input ->
                        val decision = input.toIntOrNull()
                        when {
                            suggestions.isNotEmpty()
                                    && decision != null
                                    && decision in suggestions.indices -> suggestions[decision]
                            decision == providePersonId -> promptPersonId()
                            decision == createNewPerson -> createNewPerson(registration)
                            decision == abort -> null
                            else -> throw UsageError("Not a valid choice: $input")
                        }
                    } ) ?: throw Abort()
                    val signage = crispyFishParticipantMapper.toCoreSignage(context, registration)
                    val key = Event.CrispyFishMetadata.PeopleMapKey(
                        signage = signage,
                        firstName = registration.firstName,
                        lastName = registration.lastName
                    )
                    peopleMap[key] = person
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
                peopleMap = peopleMap
            )
        )
        service.update(
            update = update,
            context = context
        )
    }
}