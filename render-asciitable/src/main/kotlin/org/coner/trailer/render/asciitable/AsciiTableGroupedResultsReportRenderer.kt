package org.coner.trailer.render.asciitable

import de.vandermeer.asciitable.AsciiTable
import org.coner.trailer.Event
import org.coner.trailer.eventresults.GroupedResultsReport

class AsciiTableGroupedResultsReportRenderer(
    columns: List<AsciiTableResultsReportColumn>
) : AsciiTableResultsReportRenderer<GroupedResultsReport>(columns) {

    override fun partial(event: Event, report: GroupedResultsReport): (AsciiTable) -> Unit = { at ->
        for ((grouping, results) in report.groupingsToResultsMap) {
            at.addRow(expandToRow(grouping.name))
            for (result in results) {
                at.addRow(columns.map { column -> column.data.invoke(result) })
            }
        }
    }
}