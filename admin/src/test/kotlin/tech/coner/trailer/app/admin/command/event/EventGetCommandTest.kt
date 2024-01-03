package tech.coner.trailer.app.admin.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.*
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.TestEvents
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.presentation.adapter.EventDetailModelAdapter
import tech.coner.trailer.presentation.model.EventDetailModel
import tech.coner.trailer.presentation.text.view.TextView

class EventGetCommandTest : BaseDataSessionCommandTest<EventGetCommand>() {

    private val service: EventService by instance()
    private val adapter: EventDetailModelAdapter by instance()
    private val view: TextView<EventDetailModel> by instance()

    override fun DirectDI.createCommand() = instance<EventGetCommand>()

    @Test
    fun `It should get event by ID`() {
        val event = TestEvents.Lscc2019.points1
        coEvery { service.findByKey(event.id) } returns Result.success(event)
        val model: EventDetailModel = mockk()
        every { adapter(any()) } returns model
        val viewRendered = "view rendered event ${event.id}"
        every { view(any()) } returns viewRendered

        val testResult = command.test(arrayOf(
            "${event.id}"
        ))

        coVerifySequence {
            service.findByKey(event.id)
            adapter(event)
            view(model)
        }
        confirmVerified(service, adapter, view)
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
    }
}