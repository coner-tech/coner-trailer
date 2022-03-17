package tech.coner.trailer.render.json.model

import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.GroupEventResults
import tech.coner.trailer.render.json.identifier.EventIdentifier

class GroupedEventResultsModel(
    val event: EventIdentifier,
    val results: ResultsModel
) {
    constructor(event: Event, results: GroupEventResults) : this(
        event = EventIdentifier(event),
        results = ResultsModel(results)
    )

    class ResultsModel(
        val type: String,
        val runCount: Int,
        val groupParticipantResults: Map<String, List<ParticipantResultModel>>,
        val topTimes: Map<String, ParticipantResultModel>
    ) {
        constructor(results: GroupEventResults) : this(
            type = results.type.key,
            runCount = results.runCount,
            groupParticipantResults = results.groupParticipantResults
                .map { (group, results) ->
                    group.abbreviation to results.map { ParticipantResultModel(it) }
                }
                .toMap(),
            topTimes = results.parentClassTopTimes
                .map { (parent, participantResult) -> parent.name to ParticipantResultModel(participantResult) }
                .toMap()
        )
    }
}