package tech.coner.trailer.presentation.model

import tech.coner.trailer.Event
import tech.coner.trailer.Participant
import tech.coner.trailer.presentation.adapter.ParticipantModelAdapter

data class ParticipantModel(
    private val participant: Participant,
    private val event: Event,
    private val adapter: ParticipantModelAdapter
) : Model {
    val signage
        get() = adapter.signageAdapter(participant.signage, event.policy)
    val firstName
        get() = adapter.firstNameAdapter(participant)
    val lastName
        get() = adapter.lastNameAdapter(participant)
    val fullName
        get() = adapter.fullNameAdapter(participant)
    val clubMemberId: String
        get() = adapter.clubMemberIdAdapter(participant)
    val carModel: String
        get() = adapter.carModelAdapter(participant.car)
    val carColor
        get() = adapter.carColorAdapter(participant.car)
}
