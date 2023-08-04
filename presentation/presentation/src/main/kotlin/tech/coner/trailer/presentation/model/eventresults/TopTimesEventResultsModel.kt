package tech.coner.trailer.presentation.model.eventresults

import tech.coner.trailer.eventresults.TopTimesEventResults
import tech.coner.trailer.presentation.adapter.eventresults.TopTimesEventResultsModelAdapter

class TopTimesEventResultsModel(
    override val eventResults: TopTimesEventResults,
    override val adapter: TopTimesEventResultsModelAdapter
) : EventResultsModel<TopTimesEventResults>() {
    val topTimes
        get() = adapter.topTimesEventResultsCollectionModelAdapter(eventResults)
}