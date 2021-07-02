package org.coner.trailer.render

import org.coner.trailer.Event
import org.coner.trailer.eventresults.EventResults

interface EventResultsRenderer<ER : EventResults, FO, PO> : Renderer {

    fun render(event: Event, results: ER): FO

    fun partial(event: Event, results: ER): PO
}