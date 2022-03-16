package org.coner.trailer.render.text

import org.coner.trailer.Event
import org.coner.trailer.eventresults.ComprehensiveEventResults
import org.coner.trailer.render.ComprehensiveEventResultsRenderer

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
            results.groupEventResults.forEach {
                appendLine(
                    groupRenderer.render(event, it)
                        .replaceFirst(nameLineMatcher, "")
                        .replaceFirst(dateLineMatcher, "")
                )
            }
        }
    }
}