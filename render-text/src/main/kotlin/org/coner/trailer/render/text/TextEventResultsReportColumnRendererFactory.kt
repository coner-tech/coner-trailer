package org.coner.trailer.render.text

import org.coner.trailer.render.EventResultsReportColumn
import org.coner.trailer.render.EventResultsReportColumnRendererFactory

class TextEventResultsReportColumnRendererFactory : EventResultsReportColumnRendererFactory<TextResultsReportColumn> {

    override fun factory(columns: List<EventResultsReportColumn>): List<TextResultsReportColumn> {
        return columns.map { column -> when (column) {
            EventResultsReportColumn.POSITION -> TextResultsReportColumn.Position()
            EventResultsReportColumn.SIGNAGE -> TextResultsReportColumn.Signage()
            EventResultsReportColumn.NAME -> TextResultsReportColumn.Name()
            EventResultsReportColumn.CAR_MODEL -> TextResultsReportColumn.Car()
            EventResultsReportColumn.SCORE -> TextResultsReportColumn.Score()
            EventResultsReportColumn.DIFF_FIRST -> TextResultsReportColumn.DiffFirst()
            EventResultsReportColumn.DIFF_PREVIOUS -> TextResultsReportColumn.DiffPrevious()
            EventResultsReportColumn.RUNS -> TextResultsReportColumn.Runs()
        } }
    }
}