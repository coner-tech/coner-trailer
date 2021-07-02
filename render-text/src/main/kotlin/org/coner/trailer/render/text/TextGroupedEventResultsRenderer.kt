package org.coner.trailer.render.text

import org.coner.trailer.Event
import org.coner.trailer.eventresults.GroupedEventResults

class TextGroupedEventResultsRenderer(
    columns: List<TextEventResultsColumn>
) : TextEventResultsRenderer<GroupedEventResults>(columns) {

    override fun partial(event: Event, results: GroupedEventResults): () -> String = {
        val sb = StringBuilder()
        for ((grouping, groupingResults) in results.groupingsToResultsMap) {
            sb.appendLine(grouping.name)
            val at = createAsciiTableWithHeaderRow(results)
            for (result in groupingResults) {
                at.addRow(columns.map { column -> column.data.invoke(result) })
            }
            at.addRule()
            sb.appendLine(at.render())
        }
        sb.toString()
    }
}