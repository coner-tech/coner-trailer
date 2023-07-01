package tech.coner.trailer.render.text.internal

import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.eventresults.Score
import tech.coner.trailer.render.Text
import tech.coner.trailer.render.internal.ParticipantResultScoreRenderer

class TextParticipantResultScoreRenderer : ParticipantResultScoreRenderer {

    override fun render(model: ParticipantResult): String {
        return when (model.score.penalty) {
            Score.Penalty.DidNotFinish -> Text.didNotFinish
            Score.Penalty.Disqualified -> Text.disqualified
            is Score.Penalty.Cone, null -> model.score.value.toString()
        }
    }
}