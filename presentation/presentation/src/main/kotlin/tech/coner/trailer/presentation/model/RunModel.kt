package tech.coner.trailer.presentation.model

import tech.coner.trailer.Event
import tech.coner.trailer.Run
import tech.coner.trailer.presentation.adapter.RunModelAdapter
import tech.coner.trailer.presentation.library.model.Model

class RunModel(
    private val event: Event,
    private val run: Run,
    private val adapter: RunModelAdapter
) : Model {
    val sequence
        get() = adapter.sequenceStringFieldAdapter(run)
    val signage
        get() = adapter.signageStringFieldAdapter(run.signage, event.policy)
    val participantName
        get() = adapter.nullableParticipantFullNameStringFieldAdapter(run.participant)
    val participantCarModel
        get() = adapter.carModelStringFieldAdapter(run.participant?.car)
    val participantCarColor
        get() = adapter.carColorStringFieldAdapter(run.participant?.car)
    val time
        get() = adapter.nullableTimeStringFieldAdapter(run.time)
    val penalties
        get() = adapter.penaltyCollectionStringFieldAdapter(run.allPenalties)
    val rerun
        get() = adapter.rerunStringFieldAdapter(run)
}