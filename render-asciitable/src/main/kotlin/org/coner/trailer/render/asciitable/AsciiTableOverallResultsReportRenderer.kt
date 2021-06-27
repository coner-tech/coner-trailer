package org.coner.trailer.render.asciitable

import de.vandermeer.asciitable.AsciiTable
import org.coner.trailer.Event
import org.coner.trailer.eventresults.OverallResultsReport

class AsciiTableOverallResultsReportRenderer(
    columns: List<AsciiTableResultsReportColumn>
) : AsciiTableResultsReportRenderer<OverallResultsReport>(columns) {

    override fun partial(event: Event, report: OverallResultsReport): (AsciiTable) -> Unit = { at ->
        for (participantResult in report.participantResults) {
            at.addRow(columns.map { column -> column.data.invoke(participantResult) })
        }
    }
}