package tech.coner.trailer.render.text.eventresults

import tech.coner.trailer.eventresults.ComprehensiveEventResults
import tech.coner.trailer.render.PartialRenderer
import tech.coner.trailer.render.eventresults.ClassEventResultsRenderer
import tech.coner.trailer.render.eventresults.ComprehensiveEventResultsRenderer
import tech.coner.trailer.render.eventresults.OverallEventResultsRenderer
import tech.coner.trailer.render.eventresults.TopTimesEventResultsRenderer
import tech.coner.trailer.render.internal.*

class TextComprehensiveEventResultsRenderer(
    signageRenderer: SignageRenderer,
    participantNameRenderer: ParticipantNameRenderer,
    carModelRenderer: CarModelRenderer,
    participantResultDiffRenderer: ParticipantResultDiffRenderer,
    participantResultScoreRenderer: ParticipantResultScoreRenderer,
    private val overallRenderer: OverallEventResultsRenderer,
    private val classRenderer: ClassEventResultsRenderer,
    private val topTimesRenderer: TopTimesEventResultsRenderer,
) : TextEventResultsRenderer<ComprehensiveEventResults>(
    signageRenderer = signageRenderer,
    participantNameRenderer = participantNameRenderer,
    carModelRenderer = carModelRenderer,
    participantResultDiffRenderer = participantResultDiffRenderer,
    participantResultScoreRenderer = participantResultScoreRenderer
), ComprehensiveEventResultsRenderer, PartialRenderer<ComprehensiveEventResults, StringBuilder> {

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