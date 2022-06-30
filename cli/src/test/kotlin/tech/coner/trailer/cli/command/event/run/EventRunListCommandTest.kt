package tech.coner.trailer.cli.command.event.run

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.bindFactory
import org.kodein.di.instance
import tech.coner.trailer.Run
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.di.Format
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.RunService
import tech.coner.trailer.render.RunRenderer

@ExtendWith(MockKExtension::class)
class EventRunListCommandTest : DIAware {

    lateinit var command: EventRunListCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
        bindFactory { _: Format -> renderer }
    }
    override val diContext = diContext { command.diContext.value }

    private val eventService: EventService by instance()
    private val runService: RunService by instance()
    @MockK lateinit var renderer: RunRenderer

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = EventRunListCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should list event runs`() {
        val event = TestEvents.Lscc2019.points1
        val runs = emptyList<Run>()
        every { eventService.findById(any()) } returns event
        every { runService.list(any()) } returns Result.success(runs)
        val render = "runRenderer rendered runs"
        every { renderer.render(runs) } returns render

        command.parse(arrayOf("${event.id}"))

        verifySequence {
            eventService.findById(event.id)
            runService.list(event)
            renderer.render(runs)
        }
        assertThat(testConsole).output().isEqualTo(render)
    }

}