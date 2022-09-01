package tech.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.context
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.EventService

@ExtendWith(MockKExtension::class)
class EventDeleteCommandTest : DIAware {

    lateinit var command: EventDeleteCommand

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkDatabaseModule())
    }
    override val diContext = diContext { command.diContext.value }

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    private val service: EventService by instance()

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = EventDeleteCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should delete event`() {
        val event = TestEvents.Lscc2019.points1
        coEvery { service.findByKey(event.id) } returns Result.success(event)
        coJustRun { service.delete(event) }

        command.parse(arrayOf("${event.id}"))

        coVerifySequence {
            service.findByKey(event.id)
            service.delete(event)
        }
    }
}