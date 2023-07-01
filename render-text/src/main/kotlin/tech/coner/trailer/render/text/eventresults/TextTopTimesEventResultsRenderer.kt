package tech.coner.trailer.render.text.eventresults

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.eventresults.TopTimesEventResults
import tech.coner.trailer.render.PartialRenderer
import tech.coner.trailer.render.eventresults.TopTimesEventResultsRenderer
import tech.coner.trailer.render.internal.*

class TextTopTimesEventResultsRenderer(
    signageRenderer: SignageRenderer,
    private val participantNameRenderer: ParticipantNameRenderer,
    carModelRenderer: CarModelRenderer,
    private val participantResultScoreRenderer: ParticipantResultScoreRenderer,
    participantResultDiffRenderer: ParticipantResultDiffRenderer
) : TextEventResultsRenderer<TopTimesEventResults>(
    signageRenderer = signageRenderer,
    participantNameRenderer = participantNameRenderer,
    carModelRenderer = carModelRenderer,
    participantResultScoreRenderer = participantResultScoreRenderer,
    participantResultDiffRenderer = participantResultDiffRenderer
), TopTimesEventResultsRenderer, PartialRenderer<TopTimesEventResults, StringBuilder> {

    override fun StringBuilder.renderPartial(model: TopTimesEventResults) {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("Category", "Name", model.eventContext.event.policy.topTimesEventResultsMethod.scoreColumnHeading)
        at.addRule()
        model.topTimes.entries.forEach { (classParent, participantResult) ->
            at.addRow(
                classParent.name,
                participantNameRenderer(participantResult.participant),
                participantResultScoreRenderer(participantResult)
            )
        }
        at.addRule()
        appendLine(at.render())
    }
}