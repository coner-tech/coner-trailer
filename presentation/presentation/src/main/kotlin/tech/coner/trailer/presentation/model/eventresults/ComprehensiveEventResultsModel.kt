package tech.coner.trailer.presentation.model.eventresults

import tech.coner.trailer.eventresults.ComprehensiveEventResults
import tech.coner.trailer.presentation.adapter.eventresults.ComprehensiveEventResultsModelAdapter

class ComprehensiveEventResultsModel(
    override val eventResults: ComprehensiveEventResults,
    override val adapter: ComprehensiveEventResultsModelAdapter,
    val overallEventResults: Collection<OverallEventResultsModel>,
    val classEventResults: ClassEventResultsModel,
    val topTimesEventResults: TopTimesEventResultsModel
) : EventResultsModel<ComprehensiveEventResults>() {
}