package org.coner.trailer.render.json.model

import org.coner.trailer.Event
import org.coner.trailer.eventresults.GroupEventResults
import org.coner.trailer.eventresults.EventResultsType
import org.coner.trailer.render.json.identifier.EventIdentifier

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