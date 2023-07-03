package tech.coner.trailer.render.text.property.eventresults

import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.eventresults.Score
import tech.coner.trailer.render.property.NullableTimePropertyRenderer
import tech.coner.trailer.render.property.Strings
import tech.coner.trailer.render.property.eventresults.ParticipantResultDiffPropertyRenderer
import tech.coner.trailer.render.property.eventresults.ParticipantResultScoreRenderer

class TextParticipantResultDiffPropertyRenderer(
    private val nullableTimePropertyRenderer: NullableTimePropertyRenderer
) : ParticipantResultDiffPropertyRenderer {

    override fun render(model: ParticipantResultDiffPropertyRenderer.Model): String {
        return when (model.participantResult.position) {
            1 -> ""
            else -> nullableTimePropertyRenderer(model.selectDiffFn(model.participantResult))
        }
    }
}

class TextParticipantResultScoreRenderer : ParticipantResultScoreRenderer {

    override fun render(model: ParticipantResult): String {
        return when (model.score.penalty) {
            Score.Penalty.DidNotFinish -> Strings.Abbreviations.didNotFinish
            Score.Penalty.Disqualified -> Strings.Abbreviations.disqualified
            is Score.Penalty.Cone, null -> model.score.value.toString()
        }
    }
}