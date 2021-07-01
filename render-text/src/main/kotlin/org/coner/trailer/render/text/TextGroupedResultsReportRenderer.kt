package org.coner.trailer.render.text

import org.coner.trailer.Event
import org.coner.trailer.eventresults.GroupedResultsReport

class TextGroupedResultsReportRenderer(
    columns: List<TextResultsReportColumn>
) : TextResultsReportRenderer<GroupedResultsReport>(columns) {

    override fun partial(event: Event, report: GroupedResultsReport): () -> String = {
        val sb = StringBuilder()
        for ((grouping, results) in report.groupingsToResultsMap) {
            sb.appendLine(grouping.name)
            val at = createAsciiTableWithHeaderRow(report)
            for (result in results) {
                at.addRow(columns.map { column -> column.data.invoke(result) })
            }
            at.addRule()
            sb.appendLine(at.render())
        }
        sb.toString()
    }
}