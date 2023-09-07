package tech.coner.trailer.presentation.view.json.internal.model

import tech.coner.trailer.eventresults.ComprehensiveEventResults
import tech.coner.trailer.presentation.view.json.internal.identifier.EventIdentifier

class ComprehensiveEventResultsModel(
    val event: EventIdentifier,
    val results: ResultsModel
) {
    constructor(results: ComprehensiveEventResults) : this(
        event = EventIdentifier(results.eventContext.event),
        results = ResultsModel(results)
    )

    class ResultsModel(
        val type: String,
        val runCount: Int,
        val overalls: List<OverallEventResultsModel.ResultsModel>,
        val groups: List<ClazzEventResultsModel.ResultsModel>
    ) {
        constructor(
            results: ComprehensiveEventResults
        ) : this (
            type = results.type.key,
            runCount = results.runCount,
            overalls = results.overallEventResults.map { OverallEventResultsModel.ResultsModel(it) },
            groups = listOf(ClazzEventResultsModel.ResultsModel(results.classEventResults))
        )
    }
}