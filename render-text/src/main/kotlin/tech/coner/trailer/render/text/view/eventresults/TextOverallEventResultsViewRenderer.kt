package tech.coner.trailer.render.text.view.eventresults

import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.render.PartialRenderer
import tech.coner.trailer.render.property.*
import tech.coner.trailer.render.property.eventresults.ParticipantResultDiffPropertyRenderer
import tech.coner.trailer.render.property.eventresults.ParticipantResultScoreRenderer

class TextOverallEventResultsViewRenderer(
    signagePropertyRenderer: SignagePropertyRenderer,
    participantNamePropertyRenderer: ParticipantNamePropertyRenderer,
    carModelPropertyRenderer: CarModelPropertyRenderer,
    participantResultDiffPropertyRenderer: ParticipantResultDiffPropertyRenderer,
    participantResultScoreRenderer: ParticipantResultScoreRenderer
) : TextEventResultsViewRenderer<OverallEventResults>(
    signagePropertyRenderer = signagePropertyRenderer,
    participantNamePropertyRenderer = participantNamePropertyRenderer,
    carModelPropertyRenderer = carModelPropertyRenderer,
    participantResultScoreRenderer = participantResultScoreRenderer,
    participantResultDiffPropertyRenderer = participantResultDiffPropertyRenderer
), PartialRenderer<OverallEventResults, StringBuilder> {

    override fun StringBuilder.renderPartial(model: OverallEventResults) {
        val at = createAsciiTable()
        at.appendHeader()
        for (participantResult in model.participantResults) {
            at.appendData(participantResult)
        }
        at.addRule()
        appendLine(at.render())
    }
}