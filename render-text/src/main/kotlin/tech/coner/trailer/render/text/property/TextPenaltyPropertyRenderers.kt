package tech.coner.trailer.render.text.property

import tech.coner.trailer.Run
import tech.coner.trailer.render.Strings
import tech.coner.trailer.render.property.PenaltiesPropertyRenderer
import tech.coner.trailer.render.property.PenaltyPropertyRenderer

class TextPenaltyPropertyRenderer : PenaltyPropertyRenderer {
    override fun render(model: Run.Penalty): String {
        return when (model) {
            Run.Penalty.Disqualified -> Strings.abbreviationDisqualified
            Run.Penalty.DidNotFinish -> Strings.abbreviationDidNotFinish
            is Run.Penalty.Cone -> "+${model.count}"
        }
    }
}

class TextPenaltiesPropertyRenderer(
    private val penaltyPropertyRenderer: PenaltyPropertyRenderer,
    private val nullRuns: String = ""
) : PenaltiesPropertyRenderer {
    override fun render(model: List<Run.Penalty>?): String {
        return model?.joinToString(transform = penaltyPropertyRenderer::render) ?: nullRuns
    }
}
