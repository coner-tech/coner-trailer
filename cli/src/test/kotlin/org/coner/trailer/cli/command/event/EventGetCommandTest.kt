package org.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.coner.trailer.TestEvents
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
class EventGetCommandTest {

    lateinit var command: EventGetCommand

    @MockK lateinit var service: EventService
    @MockK lateinit var view: EventView

    lateinit var testConsole: StringBufferConsole

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        command = EventGetCommand(
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
    fun `It should get event by ID`() {
        val event = TestEvents.Lscc2019.points1
        every { service.findById(event.id) } returns event
        val viewRendered = "view rendered event ${event.id}"
        every { view.render(event) } returns viewRendered

        command.parse(arrayOf(
            "${event.id}"
        ))

        verifySequence {
            service.findById(event.id)
            view.render(event)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }
}