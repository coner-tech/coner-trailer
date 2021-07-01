package org.coner.trailer.render.html

import org.coner.trailer.render.EventResultsReportColumn
import org.coner.trailer.render.EventResultsReportColumnRendererFactory

class HtmlEventResultsReportColumnRendererFactory : EventResultsReportColumnRendererFactory<HtmlResultsReportColumn> {

    override fun factory(columns: List<EventResultsReportColumn>): List<HtmlResultsReportColumn> {
        val renderers = mutableListOf<HtmlResultsReportColumn>()
        val skipColumns = mutableSetOf<EventResultsReportColumn>()
        columns.forEachIndexed { index, column ->
            if (column == EventResultsReportColumn.POSITION
                && columns.getOrNull(index + 1) == EventResultsReportColumn.SIGNAGE
            ) {
                skipColumns += EventResultsReportColumn.POSITION
                skipColumns += EventResultsReportColumn.SIGNAGE
                renderers += HtmlResultsReportColumn.Position()
                renderers += HtmlResultsReportColumn.Signage()
                renderers += HtmlResultsReportColumn.MobilePositionSignage()
            }
            if (column == EventResultsReportColumn.NAME
                && columns.getOrNull(index + 1) == EventResultsReportColumn.CAR_MODEL
            ) {
                skipColumns += EventResultsReportColumn.NAME
                skipColumns += EventResultsReportColumn.CAR_MODEL
                renderers += HtmlResultsReportColumn.Name()
                renderers += HtmlResultsReportColumn.CarModel()
                renderers += HtmlResultsReportColumn.MobileNameCarModel()
            }
            if (skipColumns.contains(column)) return@forEachIndexed
            val renderer = when (column) {
                EventResultsReportColumn.POSITION -> HtmlResultsReportColumn.Position()
                EventResultsReportColumn.SIGNAGE -> HtmlResultsReportColumn.Signage()
                EventResultsReportColumn.NAME -> HtmlResultsReportColumn.Name()
                EventResultsReportColumn.CAR_MODEL -> HtmlResultsReportColumn.CarModel()
                EventResultsReportColumn.SCORE -> HtmlResultsReportColumn.Score()
                EventResultsReportColumn.DIFF_FIRST -> HtmlResultsReportColumn.DiffFirst()
                EventResultsReportColumn.DIFF_PREVIOUS -> HtmlResultsReportColumn.DiffPrevious()
                EventResultsReportColumn.RUNS -> HtmlResultsReportColumn.Runs()
            }
            renderers += renderer
        }
        return renderers
    }
}