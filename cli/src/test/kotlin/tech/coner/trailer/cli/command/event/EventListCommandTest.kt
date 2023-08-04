package tech.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.presentation.model.EventDetailCollectionModel
import tech.coner.trailer.presentation.model.EventDetailModel
import tech.coner.trailer.presentation.text.view.TextCollectionView

class EventListCommandTest : BaseDataSessionCommandTest<EventListCommand>() {

    private val service: EventService by instance()
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
        val viewRendered = "view rendered events"
//        every { view(events) } returns viewRendered

        command.parse(emptyArray())

        verifySequence {
            service.list()
//            view(events)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }
}