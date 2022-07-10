package tech.coner.trailer.render.json.model

import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.ComprehensiveEventResults
import tech.coner.trailer.render.json.identifier.EventIdentifier

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
            groups = listOf(GroupedEventResultsModel.ResultsModel(results.clazzEventResults))
        )
    }
}