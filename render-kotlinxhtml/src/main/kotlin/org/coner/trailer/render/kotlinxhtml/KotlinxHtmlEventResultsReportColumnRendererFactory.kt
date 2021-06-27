package org.coner.trailer.render.kotlinxhtml

import org.coner.trailer.render.EventResultsReportColumn
import org.coner.trailer.render.EventResultsReportColumnRendererFactory

class KotlinxHtmlEventResultsReportColumnRendererFactory : EventResultsReportColumnRendererFactory<KotlinxHtmlResultsReportColumn> {

    override fun factory(columns: List<EventResultsReportColumn>): List<KotlinxHtmlResultsReportColumn> {
        val renderers = mutableListOf<KotlinxHtmlResultsReportColumn>()
        val skipColumns = mutableSetOf<EventResultsReportColumn>()
        columns.forEachIndexed { index, column ->
            if (column == EventResultsReportColumn.POSITION
                && columns.getOrNull(index + 1) == EventResultsReportColumn.SIGNAGE
            ) {
                skipColumns += EventResultsReportColumn.POSITION
                skipColumns += EventResultsReportColumn.SIGNAGE
                renderers += KotlinxHtmlResultsReportColumn.Position()
                renderers += KotlinxHtmlResultsReportColumn.Signage()
                renderers += KotlinxHtmlResultsReportColumn.MobilePositionSignage()
            }
            if (column == EventResultsReportColumn.NAME
                && columns.getOrNull(index + 1) == EventResultsReportColumn.CAR_MODEL
            ) {
                skipColumns += EventResultsReportColumn.NAME
                skipColumns += EventResultsReportColumn.CAR_MODEL
                renderers += KotlinxHtmlResultsReportColumn.Name()
                renderers += KotlinxHtmlResultsReportColumn.CarModel()
                renderers += KotlinxHtmlResultsReportColumn.MobileNameCarModel()
            }
            if (skipColumns.contains(column)) return@forEachIndexed
            val renderer = when (column) {
                EventResultsReportColumn.POSITION -> KotlinxHtmlResultsReportColumn.Position()
                EventResultsReportColumn.SIGNAGE -> KotlinxHtmlResultsReportColumn.Signage()
                EventResultsReportColumn.NAME -> KotlinxHtmlResultsReportColumn.Name()
                EventResultsReportColumn.CAR_MODEL -> KotlinxHtmlResultsReportColumn.CarModel()
                EventResultsReportColumn.SCORE -> KotlinxHtmlResultsReportColumn.Score()
                EventResultsReportColumn.DIFF_FIRST -> KotlinxHtmlResultsReportColumn.DiffFirst()
                EventResultsReportColumn.DIFF_PREVIOUS -> KotlinxHtmlResultsReportColumn.DiffPrevious()
                EventResultsReportColumn.RUNS -> KotlinxHtmlResultsReportColumn.Runs()
            }
            renderers += renderer
        }
        return renderers
    }
}