package tech.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.coEvery
import io.mockk.coVerifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.presentation.model.EventDetailModel
import tech.coner.trailer.presentation.text.view.TextView

class EventGetCommandTest : BaseDataSessionCommandTest<EventGetCommand>() {

    private val service: EventService by instance()
    private val view: TextView<EventDetailModel> by instance()

    override fun DirectDI.createCommand() = instance<EventGetCommand>()

    @Test
    fun `It should get event by ID`() {
        val event = TestEvents.Lscc2019.points1
        coEvery { service.findByKey(event.id) } returns Result.success(event)
        val viewRendered = "view rendered event ${event.id}"

//        every { view(event) } returns viewRendered

        command.parse(arrayOf(
            "${event.id}"
        ))

        coVerifySequence {
            service.findByKey(event.id)
//            view(event)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }
}