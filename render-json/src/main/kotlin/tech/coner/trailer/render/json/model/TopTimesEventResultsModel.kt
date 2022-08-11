package tech.coner.trailer.render.json.model

import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.TopTimesEventResults
import tech.coner.trailer.render.json.identifier.EventIdentifier

class TopTimesEventResultsModel(
    val event: EventIdentifier,
    val results: ResultsModel
) {
    constructor(event: Event, results: TopTimesEventResults) : this(
        event = EventIdentifier(event),
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