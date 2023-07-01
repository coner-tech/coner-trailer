package tech.coner.trailer.render.text.internal

import tech.coner.trailer.Participant
import tech.coner.trailer.render.internal.ParticipantNameRenderer

class TextParticipantNameRenderer : ParticipantNameRenderer {

    override fun render(model: Participant) = when {
        model.firstName?.isNotBlank() == true && model.lastName?.isNotBlank() == true -> "${model.firstName} ${model.lastName}"
        else -> "{unknown}"
    }
}