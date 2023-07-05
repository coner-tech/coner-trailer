package tech.coner.trailer.cli.command.event.run

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.factory
import org.kodein.di.instance
import tech.coner.trailer.Policy
import tech.coner.trailer.Run
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.RunService
import tech.coner.trailer.render.view.RunsViewRenderer

class EventRunListCommandTest : BaseDataSessionCommandTest<EventRunListCommand>() {

    private val eventService: EventService by instance()
    private val runService: RunService by instance()
    private val viewRenderer: RunsViewRenderer by instance(Format.TEXT)

    override fun createCommand(di: DI, global: GlobalModel) = EventRunListCommand(di, global)

    @Test
    fun `It should list event runs`() {
        val event = TestEvents.Lscc2019.points1
        val runs = emptyList<Run>()
        coEvery { eventService.findByKey(any()) } returns Result.success(event)
        coEvery { runService.list(any()) } returns Result.success(runs)
        val render = "viewRenderer rendered runs"
        every { viewRenderer(runs, event.policy) } returns render

        command.parse(arrayOf("${event.id}"))

        coVerifySequence {
            eventService.findByKey(event.id)
            runService.list(event)
            viewRenderer(runs, event.policy)
        }
        assertThat(testConsole).output().isEqualTo(render)
    }

}