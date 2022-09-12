package tech.coner.trailer.render.text

import kotlinx.html.InputType
import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.ComprehensiveEventResults
import tech.coner.trailer.render.ComprehensiveEventResultsRenderer

class TextComprehensiveEventResultsRenderer(
    private val overallRenderer: TextOverallEventResultsRenderer,
    private val clazzRenderer: TextClazzEventResultsRenderer,
    private val topTimesRenderer: TextTopTimesEventResultsRenderer,
) : TextEventResultsRenderer<ComprehensiveEventResults>(emptyList()),
    ComprehensiveEventResultsRenderer<String, () -> String> {

    override fun partial(eventContext: EventContext, results: ComprehensiveEventResults): () -> String = {
        buildString {
            val nameLineMatcher = Regex("${Regex.escape(eventContext.event.name)}\n")
            val dateLineMatcher = Regex("${Regex.escape(eventContext.event.date.toString())}\n")
            results.overallEventResults.forEach {
                appendLine(
                    overallRenderer.render(eventContext, it)
                        .replaceFirst(nameLineMatcher, "")
                        .replaceFirst(dateLineMatcher, "")
                )
            }
            appendLine(
                clazzRenderer.render(eventContext, results.clazzEventResults)
                    .replaceFirst(nameLineMatcher, "")
                    .replaceFirst(dateLineMatcher, "")
            )
            appendLine(
                topTimesRenderer.render(eventContext, results.topTimesEventResults)
                    .replaceFirst(nameLineMatcher, "")
                    .replaceFirst(dateLineMatcher, "")
            )
        }
    }
}