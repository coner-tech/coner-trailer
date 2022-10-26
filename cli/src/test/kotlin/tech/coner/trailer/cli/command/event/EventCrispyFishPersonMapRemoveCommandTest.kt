package tech.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import tech.coner.trailer.*
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.cli.view.EventView
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.di.mockkServiceModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.CrispyFishClassService
import tech.coner.trailer.io.service.CrispyFishEventMappingContextService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.PersonService
import java.nio.file.Paths

@ExtendWith(MockKExtension::class)
class EventCrispyFishPersonMapRemoveCommandTest : DIAware,
    CoroutineScope {

    override val coroutineContext = Dispatchers.Main + Job()

    lateinit var command: EventCrispyFishPersonMapRemoveCommand

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkServiceModule())
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val service: EventService by instance()
    private val crispyFishClassService: CrispyFishClassService by instance()
    private val personService: PersonService by instance()
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService by instance()
    @MockK lateinit var view: EventView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = EventCrispyFishPersonMapRemoveCommand(di, global)
            .context { console = testConsole }
    }

    @AfterEach
    fun after() {
        cancel()
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
            eventControlFile = Paths.get("irrelevant"),
            classDefinitionFile = Paths.get("irrelevant"),
            peopleMap = mapOf(key to person)
        )
        val event = TestEvents.Lscc2019.points1.copy(
            crispyFish = crispyFish
        )
        coEvery { service.findByKey(event.id) } returns Result.success(event)
        every { crispyFishClassService.loadAllByAbbreviation(any()) } returns TestClasses.Lscc2019.allByAbbreviation
        every { personService.findById(person.id) } returns person
        val set = event.copy(
            crispyFish = crispyFish.copy(
                peopleMap = emptyMap()
            )
        )
        coEvery { crispyFishEventMappingContextService.load(set.crispyFish!!) } returns context
        coJustRun {
            service.update(set)
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

        coVerifySequence {
            service.findByKey(event.id)
            crispyFishClassService.loadAllByAbbreviation(crispyFish.classDefinitionFile)
            personService.findById(person.id)
            service.update(set)
            view.render(set)
        }
        assertThat(testConsole.output).isEqualTo(viewRender)
    }
}