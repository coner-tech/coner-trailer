package tech.coner.trailer.render.text.property

import tech.coner.trailer.Run
import tech.coner.trailer.render.property.PenaltiesPropertyRenderer
import tech.coner.trailer.render.property.PenaltyPropertyRenderer
import tech.coner.trailer.render.property.Strings

class TextPenaltiesPropertyRenderer(
    private val penaltyPropertyRenderer: PenaltyPropertyRenderer,
    private val nullRuns: String = ""
) : PenaltiesPropertyRenderer {
    override fun render(model: List<Run.Penalty>?): String {
        return model?.joinToString(transform = penaltyPropertyRenderer::render) ?: nullRuns
    }
}

class TextPenaltyPropertyRenderer : PenaltyPropertyRenderer {
    override fun render(model: Run.Penalty): String {
        return when (model) {
            Run.Penalty.Disqualified -> Strings.Abbreviations.disqualified
            Run.Penalty.DidNotFinish -> Strings.Abbreviations.didNotFinish
            is Run.Penalty.Cone -> "+${model.count}"
        }
    }
}