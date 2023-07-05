package tech.coner.trailer.cli.command.event.participant

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
import tech.coner.trailer.TestEvents
import tech.coner.trailer.TestParticipants
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.ParticipantService
import tech.coner.trailer.render.view.ParticipantsViewRenderer

class EventParticipantListCommandTest : BaseDataSessionCommandTest<EventParticipantListCommand>() {

    private val eventService: EventService by instance()
    private val participantService: ParticipantService by instance()
    private val viewRenderer: ParticipantsViewRenderer by instance(Format.TEXT)

    override fun createCommand(di: DI, global: GlobalModel) = EventParticipantListCommand(di, global)

    @Test
    fun `It should list event participants`() {
        val event = TestEvents.Lscc2019.points1
        val participants = listOf(
            TestParticipants.Lscc2019Points1.REBECCA_JACKSON,
            TestParticipants.Lscc2019Points1.JIMMY_MCKENZIE
        )
        coEvery { eventService.findByKey(any()) } returns Result.success(event)
        coEvery { participantService.list(any()) } returns Result.success(participants)
        val render = "viewRenderer rendered"
        every { viewRenderer(participants, event.policy) } returns render

        command.parse(arrayOf("${event.id}"))

        coVerifySequence {
            eventService.findByKey(event.id)
            participantService.list(event)
            viewRenderer(participants, event.policy)
        }
        assertThat(testConsole).output().isEqualTo(render)
    }
}