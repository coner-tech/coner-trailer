package org.coner.trailer.render.json.model

import org.coner.trailer.Event
import org.coner.trailer.eventresults.OverallEventResults
import org.coner.trailer.render.json.identifier.EventIdentifier

class OverallEventResultsModel(
    val event: EventIdentifier,
    val results: ResultsModel
) {
    constructor(event: Event, results: OverallEventResults) : this(
        event = EventIdentifier(event),
        results = ResultsModel(results)
    )

    class ResultsModel(
        val type: String,
        val runCount: Int,
        val participantResults: List<ParticipantResultModel>
    ) {
        constructor(results: OverallEventResults) : this(
            type = results.type.key,
            runCount = results.runCount,
            participantResults = results.participantResults.map { ParticipantResultModel(it) }
        )
    }
}