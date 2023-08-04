package tech.coner.trailer.cli.command.event.run

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.coEvery
import io.mockk.coVerifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.Run
import tech.coner.trailer.TestEvents
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.RunService
import tech.coner.trailer.presentation.text.view.TextRunsView

class EventRunListCommandTest : BaseDataSessionCommandTest<EventRunListCommand>() {

    private val eventService: EventService by instance()
    private val runService: RunService by instance()
    private val viewRenderer: TextRunsView by instance()

    override fun DirectDI.createCommand() = instance<EventRunListCommand>()

    @Test
    fun `It should list event runs`() {
        val event = TestEvents.Lscc2019.points1
        val runs = emptyList<Run>()
        coEvery { eventService.findByKey(any()) } returns Result.success(event)
        coEvery { runService.list(any()) } returns Result.success(runs)
        val render = "viewRenderer rendered runs"
//        every { viewRenderer(runs, event.policy) } returns render

        command.parse(arrayOf("${event.id}"))

        coVerifySequence {
            eventService.findByKey(event.id)
            runService.list(event)
//            viewRenderer(runs, event.policy)
        }
        assertThat(testConsole).output().isEqualTo(render)
    }

}