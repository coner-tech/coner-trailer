package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import org.coner.crispyfish.model.Registration
import org.coner.trailer.Participant
import org.coner.trailer.Person
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.CrispyFishRegistrationView
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.datasource.crispyfish.CrispyFishPersonMapper
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.coner.trailer.io.service.PersonService
import org.coner.trailer.io.verification.EventCrispyFishForcePersonVerification
import org.coner.trailer.io.verification.VerificationException
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
        val unforcedExactMatches = mutableListOf<Pair<Registration, Person>>()
        try {

            crispyFishVerification.verifyRegistrations(context, crispyFish.forcePeople, object  : EventCrispyFishForcePersonVerification.Callback {

                override fun onUnforcedExactMatchFound(registration: Registration, person: Person) {
                    
                }

                override fun onRegistrationWithClubMemberIdFound(registration: Registration, person: Person) {
                    TODO("Not yet implemented")
                }

                override fun onRegistrationWithoutClubMemberId(registration: Registration) {
                    enter()
                    echo("Found registration without club member ID")
                    echo(crispyFishRegistrationView.render(registration))
                    val suggestions = personService.searchByNameFrom(registration)
                    handleAmbiguousRegistration(registration, suggestions)
                    exit()
                }

                override fun onPersonWithClubMemberIdNotFound(registration: Registration) {
                    enter()
                    echo("Found registration with club member ID that didn't match any people")
                    echo(crispyFishRegistrationView.render(registration))
                    val suggestions = personService.searchByNameFrom(registration)
                    handleAmbiguousRegistration(registration, suggestions)
                    exit()
                }

                override fun onMultiplePeopleWithClubMemberIdFound(registration: Registration) {
                    enter()
                    echo("Found registration with club member ID that matched multiple people")
                    echo(crispyFishRegistrationView.render(registration))
                    val suggestions = personService.searchByClubMemberIdFrom(registration)
                    handleAmbiguousRegistration(registration, suggestions)
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
        } catch (ve: VerificationException) {
            // expected, and ignored, in the context of the assemble command
        }
        val update = event.copy(
            crispyFish = crispyFish.copy(
                forcePeople = forcePeople
            )
        )
        service.update(
            update = update,
            context = context,
            eventCrispyFishForcePersonVerificationCallback = null
        )
    }
}