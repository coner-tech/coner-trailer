package tech.coner.trailer.render.text.internal

import tech.coner.trailer.Run
import tech.coner.trailer.render.internal.PenaltiesRenderer
import tech.coner.trailer.render.internal.PenaltyRenderer

class TextPenaltiesRenderer(
    private val penaltyRenderer: PenaltyRenderer,
    private val nullRuns: String = ""
) : PenaltiesRenderer {
    override fun render(model: List<Run.Penalty>?): String {
        return model?.joinToString(transform = penaltyRenderer::render) ?: nullRuns
    }
}