package tech.coner.trailer.render.text.property

import tech.coner.trailer.Participant
import tech.coner.trailer.render.property.NullableParticipantNamePropertyRenderer
import tech.coner.trailer.render.property.ParticipantNamePropertyRenderer

class TextNullableParticipantNamePropertyRenderer(
    private val participantNamePropertyRenderer: ParticipantNamePropertyRenderer,
    private val nullParticipantName: String = ""
) : NullableParticipantNamePropertyRenderer {

    override fun render(model: Participant?): String {
        return when {
            model != null -> participantNamePropertyRenderer(model)
            else -> nullParticipantName
        }
    }
}

class TextParticipantNamePropertyRenderer : ParticipantNamePropertyRenderer {

    override fun render(model: Participant): String {
        return "${model.firstName} ${model.lastName}".trim()
    }
}