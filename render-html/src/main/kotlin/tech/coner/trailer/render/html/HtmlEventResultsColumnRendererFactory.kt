package tech.coner.trailer.render.html

import tech.coner.trailer.render.EventResultsColumn
import tech.coner.trailer.render.EventResultsColumnRendererFactory

class HtmlEventResultsColumnRendererFactory() : EventResultsColumnRendererFactory<HtmlEventResultsColumn> {

    override fun factory(columns: List<EventResultsColumn>): List<HtmlEventResultsColumn> {
        val renderers = mutableListOf<HtmlEventResultsColumn>()
        val skipColumns = mutableSetOf<EventResultsColumn>()
        columns.forEachIndexed { index, column ->
            if (column == EventResultsColumn.POSITION
                && columns.getOrNull(index + 1) == EventResultsColumn.SIGNAGE
            ) {
                skipColumns += EventResultsColumn.POSITION
                skipColumns += EventResultsColumn.SIGNAGE
                renderers += HtmlEventResultsColumn.Position()
                renderers += HtmlEventResultsColumn.Signage(responsive = true)
                renderers += HtmlEventResultsColumn.MobilePositionSignage()
            }
            if (column == EventResultsColumn.NAME
                && columns.getOrNull(index + 1) == EventResultsColumn.CAR_MODEL
            ) {
                skipColumns += EventResultsColumn.NAME
                skipColumns += EventResultsColumn.CAR_MODEL
                renderers += HtmlEventResultsColumn.Name(responsive = true)
                renderers += HtmlEventResultsColumn.CarModel()
                renderers += HtmlEventResultsColumn.MobileNameCarModel()
            }
            if (skipColumns.contains(column)) return@forEachIndexed
            val renderer = when (column) {
                EventResultsColumn.POSITION -> HtmlEventResultsColumn.Position()
                EventResultsColumn.SIGNAGE -> HtmlEventResultsColumn.Signage(responsive = false)
                EventResultsColumn.NAME -> HtmlEventResultsColumn.Name(responsive = false)
                EventResultsColumn.CAR_MODEL -> HtmlEventResultsColumn.CarModel()
                EventResultsColumn.SCORE -> HtmlEventResultsColumn.Score()
                EventResultsColumn.DIFF_FIRST -> HtmlEventResultsColumn.DiffFirst()
                EventResultsColumn.DIFF_PREVIOUS -> HtmlEventResultsColumn.DiffPrevious()
            }
            renderers += renderer
        }
        return renderers
    }
}