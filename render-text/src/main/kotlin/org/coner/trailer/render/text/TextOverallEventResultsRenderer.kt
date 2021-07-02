package org.coner.trailer.render.text

import org.coner.trailer.Event
import org.coner.trailer.eventresults.OverallEventResults

class TextOverallEventResultsRenderer(
    columns: List<TextEventResultsColumn>
) : TextEventResultsRenderer<OverallEventResults>(columns) {

    override fun partial(event: Event, results: OverallEventResults): () -> String = {
        val at = createAsciiTableWithHeaderRow(results)
        for (participantResult in results.participantResults) {
            at.addRow(columns.map { column -> column.data.invoke(participantResult) })
        }
        at.addRule()
        at.render()
    }
}