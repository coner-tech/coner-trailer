package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.context
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.EventView
import org.coner.trailer.io.service.EventService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class EventSetCommandTest {

    lateinit var command: EventSetCommand

    @MockK lateinit var service: EventService
    @MockK lateinit var view: EventView

    lateinit var testConsole: StringBufferConsole

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        command = EventSetCommand(
            di = DI {
                bind<EventService>() with instance(service)
                bind<EventView>() with instance(view)
            }
        ).apply {
            context {
                console = testConsole
            }
        }
    }

    @Test
    fun `It should set event properties`() {
        TODO()
    }

    @Test
    fun `It should keep event properties for options not passed`() {
        TODO()
    }
}