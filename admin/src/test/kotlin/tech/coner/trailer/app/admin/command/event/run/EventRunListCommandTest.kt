package tech.coner.trailer.app.admin.command.event.run

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.EventContext
import tech.coner.trailer.TestEventContexts
import tech.coner.trailer.TestEvents
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.EventContextService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.RunCollectionModel
import tech.coner.trailer.presentation.model.RunModel
import tech.coner.trailer.presentation.text.view.TextCollectionView

class EventRunListCommandTest : BaseDataSessionCommandTest<EventRunListCommand>() {

    private val eventService: EventService by instance()
    private val eventContextService: EventContextService by instance()
    private val adapter: Adapter<EventContext, RunCollectionModel> by instance()
    private val view: TextCollectionView<RunModel, RunCollectionModel> by instance()

    override fun DirectDI.createCommand() = instance<EventRunListCommand>()

    @Test
    fun `It should list event runs`() {
        val event = TestEvents.Lscc2019.points1
        val eventContext = TestEventContexts.Lscc2019.points1
        coEvery { eventService.findByKey(any()) } returns Result.success(event)
        coEvery { eventContextService.load(any()) } returns Result.success(eventContext)
        val model: RunCollectionModel = mockk()
        every { adapter(any()) } returns model
        val render = "viewRenderer rendered runs"
        every { view(model) } returns render

        val testResult = command.test(arrayOf("${event.id}"))

        coVerifySequence {
            eventService.findByKey(event.id)
            eventContextService.load(event)
            adapter(eventContext)
            view(model)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(render)
        }
    }

}