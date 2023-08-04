package tech.coner.trailer.cli.command.event.participant

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.coEvery
import io.mockk.coVerifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.TestEvents
import tech.coner.trailer.TestParticipants
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.ParticipantService
import tech.coner.trailer.presentation.model.ParticipantCollectionModel
import tech.coner.trailer.presentation.model.ParticipantModel
import tech.coner.trailer.presentation.text.view.TextCollectionView

class EventParticipantListCommandTest : BaseDataSessionCommandTest<EventParticipantListCommand>() {

    private val eventService: EventService by instance()
    private val participantService: ParticipantService by instance()
    private val viewRenderer: TextCollectionView<ParticipantModel, ParticipantCollectionModel> by instance()

    override fun DirectDI.createCommand() = instance<EventParticipantListCommand>()

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
//        every { viewRenderer(participants, event.policy) } returns render

        command.parse(arrayOf("${event.id}"))

        coVerifySequence {
            eventService.findByKey(event.id)
            participantService.list(event)
//            viewRenderer(participants, event.policy)
        }
        assertThat(testConsole).output().isEqualTo(render)
    }
}