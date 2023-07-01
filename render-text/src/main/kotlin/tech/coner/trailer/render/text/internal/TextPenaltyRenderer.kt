package tech.coner.trailer.render.text.internal

import tech.coner.trailer.Run
import tech.coner.trailer.render.Text
import tech.coner.trailer.render.internal.PenaltyRenderer

class TextPenaltyRenderer : PenaltyRenderer {
    override fun render(model: Run.Penalty): String {
        return when (model) {
            Run.Penalty.Disqualified -> Text.disqualified
            Run.Penalty.DidNotFinish -> Text.didNotFinish
            is Run.Penalty.Cone -> Text.cones(model.count)
        }
    }
}