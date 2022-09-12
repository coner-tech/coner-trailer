package tech.coner.trailer.render

import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.EventResults

interface EventResultsRenderer<ER : EventResults, FO, PO> : Renderer {

    fun render(eventContext: EventContext, results: ER): FO

    fun partial(eventContext: EventContext, results: ER): PO
}