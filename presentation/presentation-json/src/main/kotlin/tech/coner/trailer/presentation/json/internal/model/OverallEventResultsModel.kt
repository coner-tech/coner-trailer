package tech.coner.trailer.presentation.json.internal.model

import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.presentation.json.internal.identifier.EventIdentifier

class OverallEventResultsModel(
    val event: EventIdentifier,
    val results: ResultsModel
) {
    constructor(results: OverallEventResults) : this(
        event = EventIdentifier(results.eventContext.event),
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