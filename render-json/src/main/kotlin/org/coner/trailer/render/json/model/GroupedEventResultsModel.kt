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
        val type: EventResultsType,
        val runCount: Int,
        val groupParticipantResults: Map<String, List<ParticipantResultModel>>
    ) {
        constructor(results: GroupEventResults) : this(
            type = results.type,
            runCount = results.runCount,
            groupParticipantResults = results.groupParticipantResults
                .map { (grouping, results) ->
                    grouping.abbreviation to results.map { ParticipantResultModel(it) }
                }
                .toMap()
        )
    }
}