package tech.coner.trailer.render.text.internal

import tech.coner.trailer.Participant
import tech.coner.trailer.render.Renderer
import tech.coner.trailer.render.internal.NullableParticipantNameRenderer
import tech.coner.trailer.render.internal.ParticipantNameRenderer

class TextNullableParticipantNameRenderer(
    private val participantNameRenderer: ParticipantNameRenderer,
    private val nullParticipantName: String = ""
) : NullableParticipantNameRenderer {

    override fun render(model: Participant?): String {
        return when {
            model != null -> participantNameRenderer(model)
            else -> nullParticipantName
        }
    }
}