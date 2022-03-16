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
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.view.EventView
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.di.mockkDatabaseModule
import org.coner.trailer.io.TestEnvironments
import org.coner.trailer.io.service.CrispyFishClassService
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.coner.trailer.io.service.PersonService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import java.nio.file.Paths

@ExtendWith(MockKExtension::class)
class EventCrispyFishPersonMapAddCommandTest : DIAware {

    lateinit var command: EventCrispyFishPersonMapAddCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val service: EventService by instance()
    private val crispyFishClassService: CrispyFishClassService by instance()
    private val personService: PersonService by instance()
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService by instance()
    @MockK lateinit var view: EventView

    lateinit var global: GlobalModel
    lateinit var testConsole: StringBufferConsole

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = EventCrispyFishPersonMapAddCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should add a force person`(
        @MockK context: CrispyFishEventMappingContext
    ) {
        val crispyFish = Event.CrispyFishMetadata(
            eventControlFile = Paths.get("irrelevant"),
            classDefinitionFile = Paths.get("irrelevant"),
            peopleMap = emptyMap()
        )
        val event = TestEvents.Lscc2019.points1.copy(
            crispyFish = crispyFish
        )
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
        every { service.findById(event.id) } returns event
        every { crispyFishClassService.loadAllByAbbreviation(any()) } returns TestClasses.Lscc2019.allByAbbreviation
        every { personService.findById(person.id) } returns person
        val set = event.copy(
            crispyFish = crispyFish.copy(
                peopleMap = mapOf(key to person)
            )
        )
        every { crispyFishEventMappingContextService.load(set.crispyFish!!) } returns context
        justRun { service.update(set) }
        val viewRender = "view rendered"
        every { view.render(set) } returns viewRender

        command.parse(arrayOf(
            "${event.id}",
            "--handicap", classing.handicap.abbreviation,
            "--number", number,
            "--first-name", person.firstName,
            "--last-name", person.lastName,
            "--person-id", "${person.id}"
        ))

        verifySequence {
            service.findById(event.id)
            crispyFishClassService.loadAllByAbbreviation(any())
            personService.findById(person.id)
            service.update(set)
            view.render(set)
        }
        assertThat(testConsole.output).isEqualTo(viewRender)
    }
}