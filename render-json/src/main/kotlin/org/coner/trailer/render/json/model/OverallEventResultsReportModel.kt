package org.coner.trailer.render.json.model

import org.coner.trailer.eventresults.OverallResultsReport
import org.coner.trailer.render.json.identifier.EventIdentifier

class OverallEventResultsReportModel(
    val event: EventIdentifier,
    val report: OverallResultsReport
)