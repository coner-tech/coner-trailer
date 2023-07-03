package tech.coner.trailer.render.text.view.eventresults

import tech.coner.trailer.eventresults.ClassEventResults
import tech.coner.trailer.eventresults.ComprehensiveEventResults
import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.eventresults.TopTimesEventResults
import tech.coner.trailer.render.PartialRenderer
import tech.coner.trailer.render.property.*
import tech.coner.trailer.render.property.eventresults.ParticipantResultDiffPropertyRenderer
import tech.coner.trailer.render.property.eventresults.ParticipantResultScoreRenderer
import tech.coner.trailer.render.view.eventresults.EventResultsViewRenderer

class TextComprehensiveEventResultsViewRenderer(
    signagePropertyRenderer: SignagePropertyRenderer,
    participantNamePropertyRenderer: ParticipantNamePropertyRenderer,
    carModelPropertyRenderer: CarModelPropertyRenderer,
    participantResultDiffPropertyRenderer: ParticipantResultDiffPropertyRenderer,
    participantResultScoreRenderer: ParticipantResultScoreRenderer,
    private val overallRenderer: EventResultsViewRenderer<OverallEventResults>,
    private val classRenderer: EventResultsViewRenderer<ClassEventResults>,
    private val topTimesRenderer: EventResultsViewRenderer<TopTimesEventResults>,
) : TextEventResultsViewRenderer<ComprehensiveEventResults>(
    signagePropertyRenderer = signagePropertyRenderer,
    participantNamePropertyRenderer = participantNamePropertyRenderer,
    carModelPropertyRenderer = carModelPropertyRenderer,
    participantResultDiffPropertyRenderer = participantResultDiffPropertyRenderer,
    participantResultScoreRenderer = participantResultScoreRenderer
), EventResultsViewRenderer<ComprehensiveEventResults>, PartialRenderer<ComprehensiveEventResults, StringBuilder> {

    override fun StringBuilder.renderPartial(model: ComprehensiveEventResults) {
        val nameLineMatcher = Regex("${Regex.escape(model.eventContext.event.name)}\n")
        val dateLineMatcher = Regex("${Regex.escape(model.eventContext.event.date.toString())}\n")
        model.overallEventResults.forEach {
            appendLine(
                overallRenderer.render(it)
                    .replaceFirst(nameLineMatcher, "")
                    .replaceFirst(dateLineMatcher, "")
            )
        }
        appendLine(
            classRenderer.render(model.classEventResults)
                .replaceFirst(nameLineMatcher, "")
                .replaceFirst(dateLineMatcher, "")
        )
        appendLine(
            topTimesRenderer.render(model.topTimesEventResults)
                .replaceFirst(nameLineMatcher, "")
                .replaceFirst(dateLineMatcher, "")
        )
    }
}