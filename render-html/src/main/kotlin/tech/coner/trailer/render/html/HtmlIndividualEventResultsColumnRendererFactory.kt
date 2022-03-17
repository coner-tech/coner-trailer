package tech.coner.trailer.render.html

import tech.coner.trailer.render.EventResultsColumn
import tech.coner.trailer.render.EventResultsColumnRendererFactory

class HtmlIndividualEventResultsColumnRendererFactory : EventResultsColumnRendererFactory<HtmlIndividualEventResultsColumn> {

    override fun factory(columns: List<EventResultsColumn>): List<HtmlIndividualEventResultsColumn> {
        return listOf(
            HtmlIndividualEventResultsColumn.Position(),
            HtmlIndividualEventResultsColumn.Score(),
            HtmlIndividualEventResultsColumn.MobilePositionScore()
        )
    }
}