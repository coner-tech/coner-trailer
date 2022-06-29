package tech.coner.trailer.cli.command.event

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
import org.kodein.di.*
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.EventTableView
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.EventService

@ExtendWith(MockKExtension::class)
class EventListCommandTest : DIAware {

    lateinit var command: EventListCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val service: EventService by instance()
    @MockK lateinit var view: EventTableView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = EventListCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should list events`() {
        val events = listOf(
            TestEvents.Lscc2019.points1,
            TestEvents.Lscc2019.points2,
            TestEvents.Lscc2019.points3
        )
        every { service.list() } returns events
        val viewRendered = "view rendered events"
        every { view.render(events) } returns viewRendered

        command.parse(emptyArray())

        verifySequence {
            service.list()
            view.render(events)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }
}