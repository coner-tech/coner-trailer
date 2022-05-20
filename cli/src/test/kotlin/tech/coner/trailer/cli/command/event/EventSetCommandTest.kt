package tech.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import tech.coner.trailer.Event
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.EventView
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.EventService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import java.nio.file.Path
import java.time.LocalDate
import kotlin.io.path.createDirectory
import kotlin.io.path.createFile

@ExtendWith(MockKExtension::class)
class EventSetCommandTest : DIAware {

    lateinit var command: EventSetCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val service: EventService by instance()
    @MockK lateinit var view: EventView

    @TempDir lateinit var root: Path
    lateinit var crispyFish: Path

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        crispyFish = root.resolve("crispy-fish").createDirectory()
        command = EventSetCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should set event properties`(
        @MockK context: CrispyFishEventMappingContext
    ) {
        val original = TestEvents.Lscc2019.points1
        val set = original.copy(
            name = "It should set event properties",
            date = LocalDate.parse("2020-12-07"),
            crispyFish = Event.CrispyFishMetadata(
                eventControlFile = crispyFish.resolve("set-event-control-file.ecf")
                    .createFile(),
                classDefinitionFile = crispyFish.resolve("set-class-definition-file.ecf")
                    .createFile(),
                peopleMap = emptyMap()
            ),
            motorsportReg = Event.MotorsportRegMetadata(
                id = "motorsportreg-event-id"
            )
        )
        every { service.findById(original.id) } returns original
        justRun { service.update(any()) }
        val viewRendered = "view rendered set event named: ${set.name}"
        every { view.render(any()) } returns viewRendered

        command.parse(arrayOf(
            "${original.id}",
            "--name", set.name,
            "--date", "${set.date}",
            "--crispy-fish", "set",
            "--event-control-file", "${set.requireCrispyFish().eventControlFile}",
            "--class-definition-file", "${set.requireCrispyFish().classDefinitionFile}",
            "--motorsportreg", "set",
            "--msr-event-id", "${set.motorsportReg?.id}"
        ))

        verifySequence {
            service.findById(original.id)
            service.update(set)
            view.render(set)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }

    @Test
    fun `It should keep event properties for options not passed`(
        @MockK context: CrispyFishEventMappingContext
    ) {
        val original = TestEvents.Lscc2019.points1
        val crispyFish = checkNotNull(original.crispyFish) { "Expected event.crispyFish to be not null" }
        every { service.findById(original.id) } returns original
        justRun {
            service.update(original)
        }
        val viewRendered = "view rendered set event named: ${original.name}"
        every { view.render(eq(original)) } returns viewRendered

        command.parse(arrayOf(
            "${original.id}",
        ))

        verifySequence {
            service.findById(original.id)
            service.update(original)
            view.render(original)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }

}