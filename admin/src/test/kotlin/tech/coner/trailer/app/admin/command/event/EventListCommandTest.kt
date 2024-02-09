package tech.coner.trailer.app.admin.command.event

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.Event
import tech.coner.trailer.TestEvents
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.presentation.library.adapter.Adapter
import tech.coner.trailer.presentation.model.EventDetailCollectionModel
import tech.coner.trailer.presentation.model.EventDetailModel
import tech.coner.trailer.presentation.text.view.TextCollectionView

class EventListCommandTest : BaseDataSessionCommandTest<EventListCommand>() {

    private val service: EventService by instance()
    private val adapter: tech.coner.trailer.presentation.library.adapter.Adapter<Collection<Event>, EventDetailCollectionModel> by instance()
    private val view: TextCollectionView<EventDetailModel, EventDetailCollectionModel> by instance()

    override fun DirectDI.createCommand() = instance<EventListCommand>()

    @Test
    fun `It should list events`() {
        val events = listOf(
            TestEvents.Lscc2019.points1,
            TestEvents.Lscc2019.points2,
            TestEvents.Lscc2019.points3
        )
        every { service.list() } returns events
        val model = EventDetailCollectionModel(emptyList())
        every { adapter(any()) } returns model
        val viewRendered = "view rendered events"
        every { view(any()) } returns viewRendered

        val testResult = command.test(emptyArray())

        verifySequence {
            service.list()
            adapter(events)
            view(model)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
    }
}