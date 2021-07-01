package org.coner.trailer.render.json.model

import org.coner.trailer.Event
import org.coner.trailer.eventresults.GroupedResultsReport
import org.coner.trailer.eventresults.ResultsType
import org.coner.trailer.render.json.identifier.EventIdentifier

class GroupedEventResultsModel(
    val event: EventIdentifier,
    val report: Report
) {
    constructor(event: Event, report: GroupedResultsReport) : this(
        event = EventIdentifier(event),
        report = Report(report)
    )

    class Report(
        val type: ResultsType,
        val runCount: Int,
        val groupingsToResults: Map<String, List<ParticipantResultModel>>
    ) {
        constructor(report: GroupedResultsReport) : this(
            type = report.type,
            runCount = report.runCount,
            groupingsToResults = report.groupingsToResultsMap
                .map { (grouping, results) ->
                    grouping.abbreviation to results.map { ParticipantResultModel(it) }
                }
                .toMap()
        )
    }
}