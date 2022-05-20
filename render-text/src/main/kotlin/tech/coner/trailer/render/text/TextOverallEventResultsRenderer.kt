package tech.coner.trailer.render.text

import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.render.OverallEventResultsRenderer

class TextOverallEventResultsRenderer(
    columns: List<TextEventResultsColumn>
) : TextEventResultsRenderer<OverallEventResults>(columns),
    OverallEventResultsRenderer<String, () -> String> {

    override fun partial(event: Event, results: OverallEventResults): () -> String = {
        val at = createAsciiTableWithHeaderRow(results)
        for (participantResult in results.participantResults) {
            at.addRow(columns.map { column -> column.data.invoke(participantResult) })
        }
        at.addRule()
        at.render()
    }
}