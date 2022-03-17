package tech.coner.trailer.render

import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.EventResults

interface EventResultsRenderer<ER : EventResults, FO, PO> : Renderer {

    fun render(event: Event, results: ER): FO

    fun partial(event: Event, results: ER): PO
}