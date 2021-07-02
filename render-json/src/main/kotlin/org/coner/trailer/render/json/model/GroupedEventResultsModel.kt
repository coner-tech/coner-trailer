package org.coner.trailer.render.json.model

import org.coner.trailer.Event
import org.coner.trailer.eventresults.GroupedEventResults
import org.coner.trailer.eventresults.EventResultsType
import org.coner.trailer.render.json.identifier.EventIdentifier

class GroupedEventResultsModel(
    val event: EventIdentifier,
    val results: ResultsModel
) {
    constructor(event: Event, results: GroupedEventResults) : this(
        event = EventIdentifier(event),
        results = ResultsModel(results)
    )

    class ResultsModel(
        val type: EventResultsType,
        val runCount: Int,
        val groupingsToResults: Map<String, List<ParticipantResultModel>>
    ) {
        constructor(results: GroupedEventResults) : this(
            type = results.type,
            runCount = results.runCount,
            groupingsToResults = results.groupingsToResultsMap
                .map { (grouping, results) ->
                    grouping.abbreviation to results.map { ParticipantResultModel(it) }
                }
                .toMap()
        )
    }
}