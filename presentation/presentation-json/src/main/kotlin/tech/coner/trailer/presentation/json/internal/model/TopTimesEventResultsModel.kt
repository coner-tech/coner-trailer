package tech.coner.trailer.presentation.json.internal.model

import tech.coner.trailer.eventresults.TopTimesEventResults
import tech.coner.trailer.presentation.json.internal.identifier.EventIdentifier

class TopTimesEventResultsModel(
    val event: EventIdentifier,
    val results: ResultsModel
) {
    constructor(results: TopTimesEventResults) : this(
        event = EventIdentifier(results.eventContext.event),
        results = ResultsModel(results)
    )

    class ResultsModel(
        val type: String,
        val topTimes: Map<String, ParticipantResultModel>
    ) {
        constructor(results: TopTimesEventResults) : this(
            type = results.type.key,
            topTimes = results.topTimes
                .toList()
                .associate { (parent, participantResult) -> parent.name to ParticipantResultModel(participantResult) }
        )
    }
}