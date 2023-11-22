package tech.coner.trailer.cli.command.event.crispyfish

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import java.util.UUID
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.crispyfish.model.Registration
import tech.coner.trailer.Classing
import tech.coner.trailer.Event
import tech.coner.trailer.Person
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.view.CrispyFishRegistrationView
import tech.coner.trailer.datasource.crispyfish.CrispyFishClassingMapper
import tech.coner.trailer.datasource.crispyfish.CrispyFishPersonMapper
import tech.coner.trailer.io.service.CrispyFishClassService
import tech.coner.trailer.io.service.CrispyFishEventMappingContextService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.io.verifier.EventCrispyFishPersonMapVerifier
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.text.view.TextView

class EventCrispyFishPersonMapAssembleCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "person-map-assemble",
    help = "Interactively assemble the Crispy Fish person map for an event"
) {

    override val diContext = diContextDataSession()
    private val service: EventService by instance()
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService by instance()
    private val personService: PersonService by instance()
    private val crispyFishClassService: CrispyFishClassService by instance()
    private val crispyFishClassingMapper: CrispyFishClassingMapper by instance()
    private val eventCrispyFishPersonMapVerifier: EventCrispyFishPersonMapVerifier by instance()
    private val crispyFishRegistrationView: CrispyFishRegistrationView by instance()
    private val crispyFishPersonMapper: CrispyFishPersonMapper by instance()
    private val adapter: Adapter<Person, PersonDetailModel> by instance()
    private val personView: TextView<PersonDetailModel> by instance()

    private val id: UUID by argument().convert { toUuid(it) }

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val event = service.findByKey(id).getOrThrow()
        val crispyFish = event.crispyFish
        if (crispyFish == null) {
            echo("Selected Event lacks Crispy Fish Metadata")
            throw Abort()
        }
        val allClassesByAbbreviation = crispyFishClassService.loadAllByAbbreviation(crispyFish.classDefinitionFile)
        val peopleMap = mutableMapOf<Event.CrispyFishMetadata.PeopleMapKey, Person>()
        val context = crispyFishEventMappingContextService.load(event, crispyFish)
        eventCrispyFishPersonMapVerifier.verify(
            event = event,
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
                    echo(personView(adapter(person)))
                    peopleMap[key] = person
                    exit()
                }

                override fun onUnmappableFirstNameNull(registration: Registration) {
                    enter()
                    echo("Found unmappable registration with null first name.")
                    echo("Registration:")
                    echo(crispyFishRegistrationView.render(registration))
                    echo("Aborting! Use event check subcommand for comprehensive checks, and fix the crispy fish registration file before assembling the person map.")
                    throw Abort()
                }

                override fun onUnmappableLastNameNull(registration: Registration) {
                    enter()
                    echo("Found unmappable registration with null last name.")
                    echo("Registration:")
                    echo(crispyFishRegistrationView.render(registration))
                    echo("Aborting! Use event check subcommand for comprehensive checks, and fix the crispy fish registration file before assembling the person map.")
                    throw Abort()
                }

                override fun onUnmappableClassing(registration: Registration) {
                    enter()
                    echo("Found unmappable registration with null grouping.")
                    echo("Registration:")
                    echo(crispyFishRegistrationView.render(registration))
                    echo("Aborting! Use event check subcommand for comprehensive checks, and fix the crispy fish registration file before assembling the person map.")
                    throw Abort()
                }

                override fun onUnmappableNumber(registration: Registration) {
                    enter()
                    echo("Found unmappable registration with null number.")
                    echo("Registration:")
                    echo(crispyFishRegistrationView.render(registration))
                    echo("Aborting! Use event check subcommand for comprehensive checks, and fix the crispy fish registration file before assembling the person map.")
                    throw Abort()
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
                    echo(this@EventCrispyFishPersonMapAssembleCommand.personView(this@EventCrispyFishPersonMapAssembleCommand.adapter(person)))
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
                    val classing: Classing = requireNotNull(
                        crispyFishClassingMapper.toCore(
                        allClassesByAbbreviation = allClassesByAbbreviation,
                        cfRegistration = registration
                    )) { "Unable to resolve classing for exact match registration: $registration}" }
                    val key = Event.CrispyFishMetadata.PeopleMapKey(
                        classing = classing,
                        number = checkNotNull(registration.signage.number),
                        firstName = checkNotNull(registration.firstName),
                        lastName = checkNotNull(registration.lastName)
                    )
                    echo("Found unmapped registration with exact club member and name match.")
                    echo(crispyFishRegistrationView.render(registration))
                    echo("Auto-mapping to person:")
                    echo(this@EventCrispyFishPersonMapAssembleCommand.personView(this@EventCrispyFishPersonMapAssembleCommand.adapter(person)))
                    peopleMap[key] = person
                    exit()
                }

                override fun onUnused(key: Event.CrispyFishMetadata.PeopleMapKey, person: Person) {
                    enter()
                    echo("Found unused mapping. Ignoring.")
                    echo("Signage: ${("${key.classing.group?.abbreviation} ${key.classing.handicap.abbreviation} ${key.number}")}")
                    echo("Name: ${key.firstName} ${key.lastName}")
                    echo("Person:")
                    echo(this@EventCrispyFishPersonMapAssembleCommand.personView(this@EventCrispyFishPersonMapAssembleCommand.adapter(person)))
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
                    val person = prompt(text = "", promptSuffix = "> "  ) { input ->
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
                    } ?: throw Abort()
                    val classing = crispyFishClassingMapper.toCore(
                        allClassesByAbbreviation = allClassesByAbbreviation,
                        cfRegistration = registration
                    )
                    val key = Event.CrispyFishMetadata.PeopleMapKey(
                        classing = checkNotNull(classing),
                        number = checkNotNull(registration.signage.number),
                        firstName = checkNotNull(registration.firstName),
                        lastName = checkNotNull(registration.lastName)
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
            },
            context = context
        )
        val update = event.copy(
            crispyFish = crispyFish.copy(
                peopleMap = peopleMap
            )
        )
        service.update(update).getOrThrow()
        Unit
    }
}