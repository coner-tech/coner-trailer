package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.coner.trailer.TestEvents
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.io.service.EventService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class EventDeleteCommandTest {

    lateinit var command: EventDeleteCommand

    @MockK lateinit var service: EventService

    lateinit var useConsole: StringBufferConsole

    @BeforeEach
    fun before() {
        useConsole = StringBufferConsole()
        command = EventDeleteCommand(DI {
            bind<EventService>() with instance(service)
        }).context {
            console = useConsole
        }
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