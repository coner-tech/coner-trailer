package tech.coner.trailer.render.text.view.eventresults

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.eventresults.TopTimesEventResults
import tech.coner.trailer.render.PartialRenderer
import tech.coner.trailer.render.property.CarModelPropertyRenderer
import tech.coner.trailer.render.property.ParticipantNamePropertyRenderer
import tech.coner.trailer.render.property.SignagePropertyRenderer
import tech.coner.trailer.render.property.eventresults.ParticipantResultDiffPropertyRenderer
import tech.coner.trailer.render.property.eventresults.ParticipantResultScoreRenderer

class TextTopTimesEventResultsViewRenderer(
    signagePropertyRenderer: SignagePropertyRenderer,
    private val participantNamePropertyRenderer: ParticipantNamePropertyRenderer,
    carModelPropertyRenderer: CarModelPropertyRenderer,
    private val participantResultScoreRenderer: ParticipantResultScoreRenderer,
    participantResultDiffPropertyRenderer: ParticipantResultDiffPropertyRenderer
) : TextEventResultsViewRenderer<TopTimesEventResults>(
    signagePropertyRenderer = signagePropertyRenderer,
    participantNamePropertyRenderer = participantNamePropertyRenderer,
    carModelPropertyRenderer = carModelPropertyRenderer,
    participantResultScoreRenderer = participantResultScoreRenderer,
    participantResultDiffPropertyRenderer = participantResultDiffPropertyRenderer
), PartialRenderer<TopTimesEventResults, StringBuilder> {

    override fun StringBuilder.renderPartial(model: TopTimesEventResults) {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("Category", "Name", model.eventContext.event.policy.topTimesEventResultsMethod.scoreColumnHeading)
        at.addRule()
        model.topTimes.entries.forEach { (classParent, participantResult) ->
            at.addRow(
                classParent.name,
                participantNamePropertyRenderer(participantResult.participant),
                participantResultScoreRenderer(participantResult)
            )
        }
        at.addRule()
        appendLine(at.render())
    }
}