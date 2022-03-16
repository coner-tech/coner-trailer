package org.coner.trailer.render.html

import org.coner.trailer.eventresults.EventResultsType
import org.coner.trailer.render.EventResultsColumn
import org.coner.trailer.render.EventResultsColumnRendererFactory

class HtmlIndividualEventResultsColumnRendererFactory : EventResultsColumnRendererFactory<HtmlIndividualEventResultsColumn> {

    override fun factory(columns: List<EventResultsColumn>): List<HtmlIndividualEventResultsColumn> {
        return listOf(
            HtmlIndividualEventResultsColumn.Position(),
            HtmlIndividualEventResultsColumn.Score(),
            HtmlIndividualEventResultsColumn.MobilePositionScore()
        )
    }
}