package org.coner.trailer.render.asciitable

import org.coner.trailer.render.EventResultsReportColumn
import org.coner.trailer.render.EventResultsReportColumnRendererFactory

class AsciiTableEventResultsReportColumnRendererFactory : EventResultsReportColumnRendererFactory<AsciiTableResultsReportColumn> {

    override fun factory(columns: List<EventResultsReportColumn>): List<AsciiTableResultsReportColumn> {
        return columns.map { column -> when (column) {
            EventResultsReportColumn.POSITION -> AsciiTableResultsReportColumn.Position()
            EventResultsReportColumn.SIGNAGE -> AsciiTableResultsReportColumn.Signage()
            EventResultsReportColumn.NAME -> AsciiTableResultsReportColumn.Name()
            EventResultsReportColumn.CAR_MODEL -> AsciiTableResultsReportColumn.Car()
            EventResultsReportColumn.SCORE -> AsciiTableResultsReportColumn.Score()
            EventResultsReportColumn.DIFF_FIRST -> AsciiTableResultsReportColumn.DiffFirst()
            EventResultsReportColumn.DIFF_PREVIOUS -> AsciiTableResultsReportColumn.DiffPrevious()
            EventResultsReportColumn.RUNS -> AsciiTableResultsReportColumn.Runs()
        } }
    }
}