package org.coner.trailer.render.json.model

import org.coner.trailer.Event
import org.coner.trailer.eventresults.ComprehensiveEventResults
import org.coner.trailer.render.json.identifier.EventIdentifier

class ComprehensiveEventResultsModel(
    val event: EventIdentifier,
    val results: ResultsModel
) {
    constructor(event: Event, results: ComprehensiveEventResults) : this(
        event = EventIdentifier(event),
        results = ResultsModel(results)
    )

    class ResultsModel(
        val type: String,
        val runCount: Int,
        val overalls: List<OverallEventResultsModel.ResultsModel>,
        val groups: List<GroupedEventResultsModel.ResultsModel>
    ) {
        constructor(
            results: ComprehensiveEventResults
        ) : this (
            type = results.type.key,
            runCount = results.runCount,
            overalls = results.overallEventResults.map { OverallEventResultsModel.ResultsModel(it) },
            groups = results.groupEventResults.map { GroupedEventResultsModel.ResultsModel(it) }
        )
    }
}