package tech.coner.trailer.render.text.internal

import tech.coner.trailer.render.internal.NullableTimeRenderer
import tech.coner.trailer.render.internal.ParticipantResultDiffRenderer

class TextParticipantResultDiffRenderer(
    private val nullableTimeRenderer: NullableTimeRenderer
) : ParticipantResultDiffRenderer {

    override fun render(model: ParticipantResultDiffRenderer.Model): String {
        return when (model.participantResult.position) {
            1 -> ""
            else -> nullableTimeRenderer(model.selectDiffFn(model.participantResult))
        }
    }
}