package org.coner.trailer.render.json.model

import org.coner.trailer.eventresults.GroupedResultsReport
import org.coner.trailer.render.json.identifier.EventIdentifier

class GroupedEventResultsReportModel(
    val event: EventIdentifier,
    val report: GroupedResultsReport
)