package tech.coner.trailer.render.text

import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.ComprehensiveEventResults
import tech.coner.trailer.render.ComprehensiveEventResultsRenderer

class TextComprehensiveEventResultsRenderer(
    private val overallRenderer: TextOverallEventResultsRenderer,
    private val groupRenderer: TextGroupEventResultsRenderer
) : TextEventResultsRenderer<ComprehensiveEventResults>(emptyList()),
    ComprehensiveEventResultsRenderer<String, () -> String> {

    override fun partial(event: Event, results: ComprehensiveEventResults): () -> String = {
        buildString {
            val nameLineMatcher = Regex("${Regex.escape(event.name)}\n")
            val dateLineMatcher = Regex("${Regex.escape(event.date.toString())}\n")
            results.overallEventResults.forEach {
                appendLine(
                    overallRenderer.render(event, it)
                        .replaceFirst(nameLineMatcher, "")
                        .replaceFirst(dateLineMatcher, "")
                )
            }
            /*
            results.clazzEventResults.forEach {
                appendLine(
                    groupRenderer.render(event, it)
                        .replaceFirst(nameLineMatcher, "")
                        .replaceFirst(dateLineMatcher, "")
                )
            }
             */
        }
    }
}