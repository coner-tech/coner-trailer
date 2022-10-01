package tech.coner.trailer.render.text

import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.asciitable.CWC_LongestLine
import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.TopTimesEventResults
import tech.coner.trailer.render.TopTimesEventResultsRenderer

class TextTopTimesEventResultsRenderer : TextEventResultsRenderer<TopTimesEventResults>(),
    TopTimesEventResultsRenderer<String, () -> String> {

    override fun partial(eventContext: EventContext, results: TopTimesEventResults): () -> String = {
        val at = AsciiTable()
        at.renderer.cwc = CWC_LongestLine()
        at.addRule()
        at.addRow("Category", "Name", eventContext.event.policy.topTimesEventResultsMethod.scoreColumnHeading)
        at.addRule()
        results.topTimes.entries.forEach { (classParent, participantResult) ->
            at.addRow(
                classParent.name,
                renderName(participantResult.participant),
                renderScoreColumnValue(participantResult)
            )
        }
        at.addRule()
        at.render()
    }
}