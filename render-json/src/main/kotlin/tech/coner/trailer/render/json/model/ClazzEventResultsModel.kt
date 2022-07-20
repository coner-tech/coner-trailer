package tech.coner.trailer.render.json.model

import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.ClazzEventResults
import tech.coner.trailer.render.json.identifier.EventIdentifier

class ClazzEventResultsModel(
    val event: EventIdentifier,
    val results: ResultsModel
) {
    constructor(event: Event, results: ClazzEventResults) : this(
        event = EventIdentifier(event),
        results = ResultsModel(results)
    )

    class ResultsModel(
        val type: String,
        val runCount: Int,
        val clazzParticipantResults: Map<String, List<ParticipantResultModel>>,
    ) {
        constructor(results: ClazzEventResults) : this(
            type = results.type.key,
            runCount = results.runCount,
            clazzParticipantResults = results.groupParticipantResults
                .map { (group, results) ->
                    group.abbreviation to results.map { ParticipantResultModel(it) }
                }
                .toMap()
        )
    }
}