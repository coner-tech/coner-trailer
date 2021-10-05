package org.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.coner.trailer.*
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.EventView
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.io.service.CrispyFishClassService
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.coner.trailer.io.service.PersonService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class EventCrispyFishPersonMapRemoveCommandTest {

    lateinit var command: EventCrispyFishPersonMapRemoveCommand

    @MockK lateinit var service: EventService
    @MockK lateinit var crispyFishClassService: CrispyFishClassService
    @MockK lateinit var personService: PersonService
    @MockK lateinit var crispyFishEventMappingContextService: CrispyFishEventMappingContextService
    @MockK lateinit var view: EventView

    lateinit var testConsole: StringBufferConsole

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        command = EventCrispyFishPersonMapRemoveCommand(
            di = DI {
                bind<EventService>() with instance(service)
                bind<CrispyFishClassService>() with instance(crispyFishClassService)
                bind<PersonService>() with instance(personService)
                bind<CrispyFishEventMappingContextService>() with instance(crispyFishEventMappingContextService)
                bind<EventView>() with instance(view)
            }
        ).apply {
            context {
                console = testConsole
            }
        }
    }

    @Test
    fun `It should remove a force person`(
        @MockK context: CrispyFishEventMappingContext
    ) {
        val person = TestPeople.REBECCA_JACKSON
        val classing = Classing(
            group = null,
            handicap = TestClasses.Lscc2019.HS
        )
        val number = "1"
        val key = Event.CrispyFishMetadata.PeopleMapKey(
            classing = classing,
            number = number,
            firstName = person.firstName,
            lastName = person.lastName
        )
        val crispyFish = Event.CrispyFishMetadata(
            eventControlFile = "irrelevant",
            classDefinitionFile = "irrelevant",
            peopleMap = mapOf(key to person)
        )
        val event = TestEvents.Lscc2019.points1.copy(
            crispyFish = crispyFish
        )
        every { service.findById(event.id) } returns event
        every { crispyFishClassService.loadAllByAbbreviation(any()) } returns TestClasses.Lscc2019.allByAbbreviation
        every { personService.findById(person.id) } returns person
        val set = event.copy(
            crispyFish = crispyFish.copy(
                peopleMap = emptyMap()
            )
        )
        every { crispyFishEventMappingContextService.load(set.crispyFish!!) } returns context
        justRun {
            service.update(
                update = set,
                context = context
            )
        }
        val viewRender = "view rendered"
        every { view.render(set) } returns viewRender

        command.parse(arrayOf(
            "${event.id}",
            "--handicap", classing.abbreviation,
            "--number", number,
            "--first-name", person.firstName,
            "--last-name", person.lastName,
            "--person-id", "${person.id}"
        ))

        verifySequence {
            service.findById(event.id)
            crispyFishClassService.loadAllByAbbreviation(crispyFish.classDefinitionFile)
            personService.findById(person.id)
            service.update(
                update = set,
                context = context
            )
            view.render(set)
        }
        assertThat(testConsole.output).isEqualTo(viewRender)
    }
}