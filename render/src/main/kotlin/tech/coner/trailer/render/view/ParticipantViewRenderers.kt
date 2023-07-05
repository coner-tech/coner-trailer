package tech.coner.trailer.render.view

import tech.coner.trailer.Participant
import tech.coner.trailer.Policy

interface ParticipantViewRenderer : ViewRenderer<ParticipantViewRenderer.Model> {
    data class Model(val participant: Participant, val policy: Policy)

    fun render(participant: Participant, policy: Policy) = render(Model(participant, policy))
    operator fun invoke(participant: Participant, policy: Policy) = render(participant, policy)
}
interface ParticipantsViewRenderer : ViewRenderer<ParticipantsViewRenderer.Model> {
    data class Model(val participants: Collection<Participant>, val policy: Policy)

    fun render(participants: Collection<Participant>, policy: Policy) = render(Model(participants, policy))
    operator fun invoke(participants: Collection<Participant>, policy: Policy) = render(participants, policy)
}