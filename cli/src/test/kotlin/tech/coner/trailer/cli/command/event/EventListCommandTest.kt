package tech.coner.trailer.cli.command.event

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.render.view.EventCollectionViewRenderer

class EventListCommandTest : BaseDataSessionCommandTest<EventListCommand>() {

    private val service: EventService by instance()
    private val view: EventCollectionViewRenderer by instance(Format.TEXT)

    override fun createCommand(di: DI, global: GlobalModel) = EventListCommand(di, global)

    @Test
    fun `It should list events`() {
        val events = listOf(
            TestEvents.Lscc2019.points1,
            TestEvents.Lscc2019.points2,
            TestEvents.Lscc2019.points3
        )
        every { service.list() } returns events
        val viewRendered = "view rendered events"
        every { view(events) } returns viewRendered

        command.parse(emptyArray())

        verifySequence {
            service.list()
            view(events)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }
}