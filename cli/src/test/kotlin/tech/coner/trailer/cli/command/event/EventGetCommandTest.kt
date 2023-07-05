package tech.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.render.view.EventViewRenderer

class EventGetCommandTest : BaseDataSessionCommandTest<EventGetCommand>() {

    private val service: EventService by instance()
    private val view: EventViewRenderer by instance(Format.TEXT)

    override fun createCommand(di: DI, global: GlobalModel) = EventGetCommand(di, global)

    @Test
    fun `It should get event by ID`() {
        val event = TestEvents.Lscc2019.points1
        coEvery { service.findByKey(event.id) } returns Result.success(event)
        val viewRendered = "view rendered event ${event.id}"
        every { view(event) } returns viewRendered

        command.parse(arrayOf(
            "${event.id}"
        ))

        coVerifySequence {
            service.findByKey(event.id)
            view(event)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }
}