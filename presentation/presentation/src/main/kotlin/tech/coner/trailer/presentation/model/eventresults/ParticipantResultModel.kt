package tech.coner.trailer.presentation.model.eventresults

import tech.coner.trailer.eventresults.EventResults
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.presentation.adapter.eventresults.ParticipantResultModelAdapter
import tech.coner.trailer.presentation.model.Model

class ParticipantResultModel(
    private val eventResults: EventResults,
    private val participantResult: ParticipantResult,
    private val adapter: ParticipantResultModelAdapter,
) : Model {
    val position
        get() = adapter.positionAdapter(participantResult)
    val signage
        get() = adapter.signageAdapter(participantResult.participant.signage, eventResults.eventContext.event.policy)
    val name
        get() = adapter.nameAdapter(participantResult.participant)
    val carModel
        get() = adapter.carModelAdapter(participantResult.participant.car)
    val score
        get() = adapter.scoreAdapter(participantResult)
    val diffFirst
        get() = adapter.diffAdapter(participantResult, ParticipantResult::diffFirst)
    val diffPrevious
        get() = adapter.diffAdapter(participantResult, ParticipantResult::diffPrevious)
}