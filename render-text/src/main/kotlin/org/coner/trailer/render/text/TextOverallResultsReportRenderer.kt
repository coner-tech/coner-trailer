package org.coner.trailer.render.text

import org.coner.trailer.Event
import org.coner.trailer.eventresults.OverallResultsReport

class TextOverallResultsReportRenderer(
    columns: List<TextResultsReportColumn>
) : TextResultsReportRenderer<OverallResultsReport>(columns) {

    override fun partial(event: Event, report: OverallResultsReport): () -> String = {
        val at = createAsciiTableWithHeaderRow(report)
        for (participantResult in report.participantResults) {
            at.addRow(columns.map { column -> column.data.invoke(participantResult) })
        }
        at.addRule()
        at.render()
    }
}