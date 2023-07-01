package tech.coner.trailer.render.text.eventresults

import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.render.PartialRenderer
import tech.coner.trailer.render.eventresults.OverallEventResultsRenderer
import tech.coner.trailer.render.internal.*

class TextOverallEventResultsRenderer(
    signageRenderer: SignageRenderer,
    participantNameRenderer: ParticipantNameRenderer,
    carModelRenderer: CarModelRenderer,
    participantResultDiffRenderer: ParticipantResultDiffRenderer,
    participantResultScoreRenderer: ParticipantResultScoreRenderer
) : TextEventResultsRenderer<OverallEventResults>(
    signageRenderer = signageRenderer,
    participantNameRenderer = participantNameRenderer,
    carModelRenderer = carModelRenderer,
    participantResultScoreRenderer = participantResultScoreRenderer,
    participantResultDiffRenderer = participantResultDiffRenderer
), OverallEventResultsRenderer, PartialRenderer<OverallEventResults, StringBuilder> {

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