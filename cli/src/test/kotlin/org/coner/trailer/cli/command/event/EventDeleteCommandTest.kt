package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.coner.trailer.TestEvents
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.di.mockkDatabaseModule
import org.coner.trailer.io.TestEnvironments
import org.coner.trailer.io.service.EventService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class EventDeleteCommandTest : DIAware {

    lateinit var command: EventDeleteCommand

    override val di = DI.lazy {
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
        every { service.findById(event.id) } returns event
        justRun { service.delete(event) }

        command.parse(arrayOf("${event.id}"))

        verifySequence {
            service.findById(event.id)
            service.delete(event)
        }
    }
}