package org.coner.trailer.render.json.model

import org.coner.trailer.Event
import org.coner.trailer.eventresults.GroupedResultsReport

class GroupedEventResultsReportModel(
    val event: Event,
    val report: GroupedResultsReport
)