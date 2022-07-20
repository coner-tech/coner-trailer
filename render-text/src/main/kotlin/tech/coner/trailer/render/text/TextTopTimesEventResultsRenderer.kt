package tech.coner.trailer.render.text

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.TopTimesEventResults
import tech.coner.trailer.render.TopTimesEventResultsRenderer

class TextTopTimesEventResultsRenderer : TextEventResultsRenderer<TopTimesEventResults>(emptyList()),
    TopTimesEventResultsRenderer<String, () -> String> {

    override fun partial(event: Event, results: TopTimesEventResults): () -> String = {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("Category", event.policy.topTimesEventResultsMethod.scoreColumnHeading)
        at.addRule()
        results.topTimes.entries.forEach { (classParent, participantResult) ->
            at.addRow(classParent.name, renderScoreColumnValue(participantResult))
        }
        at.addRule()
        at.render()
    }
}