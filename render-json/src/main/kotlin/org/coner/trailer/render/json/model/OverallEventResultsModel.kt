package org.coner.trailer.render.json.model

import org.coner.trailer.Event
import org.coner.trailer.eventresults.OverallResultsReport
import org.coner.trailer.eventresults.ResultsType
import org.coner.trailer.render.json.identifier.EventIdentifier

class OverallEventResultsModel(
    val event: EventIdentifier,
    val report: ReportModel
) {
    constructor(event: Event, report: OverallResultsReport) : this(
        event = EventIdentifier(event),
        report = ReportModel(report)
    )

    class ReportModel(
        val type: ResultsType,
        val runCount: Int,
        val participantResults: List<ParticipantResultModel>
    ) {
        constructor(report: OverallResultsReport) : this(
            type = report.type,
            runCount = report.runCount,
            participantResults = report.participantResults.map { ParticipantResultModel(it) }
        )
    }
}