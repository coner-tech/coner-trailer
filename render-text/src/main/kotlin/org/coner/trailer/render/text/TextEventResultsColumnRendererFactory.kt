package org.coner.trailer.render.text

import org.coner.trailer.render.EventResultsColumn
import org.coner.trailer.render.EventResultsColumnRendererFactory

class TextEventResultsColumnRendererFactory : EventResultsColumnRendererFactory<TextEventResultsColumn> {

    override fun factory(columns: List<EventResultsColumn>): List<TextEventResultsColumn> {
        return columns.map { column -> when (column) {
            EventResultsColumn.POSITION -> TextEventResultsColumn.Position()
            EventResultsColumn.SIGNAGE -> TextEventResultsColumn.Signage()
            EventResultsColumn.NAME -> TextEventResultsColumn.Name()
            EventResultsColumn.CAR_MODEL -> TextEventResultsColumn.Car()
            EventResultsColumn.SCORE -> TextEventResultsColumn.Score()
            EventResultsColumn.DIFF_FIRST -> TextEventResultsColumn.DiffFirst()
            EventResultsColumn.DIFF_PREVIOUS -> TextEventResultsColumn.DiffPrevious()
            EventResultsColumn.RUNS -> TextEventResultsColumn.Runs()
        } }
    }
}