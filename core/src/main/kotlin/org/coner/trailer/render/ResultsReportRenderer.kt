package org.coner.trailer.render

import org.coner.trailer.Event
import org.coner.trailer.eventresults.ResultsReport

interface ResultsReportRenderer<RR : ResultsReport, FO, PO> : Renderer {

    fun render(event: Event, report: RR): FO

    fun partial(event: Event, report: RR): PO
}