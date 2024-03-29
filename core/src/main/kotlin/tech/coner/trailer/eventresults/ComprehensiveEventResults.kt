package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext

data class ComprehensiveEventResults(
    override val eventContext: EventContext,
    val overallEventResults: List<OverallEventResults>,
    val classEventResults: ClassEventResults,
    val topTimesEventResults: TopTimesEventResults,
    val individualEventResults: IndividualEventResults
) : EventResults {

    override val type: EventResultsType = StandardEventResultsTypes.comprehensive

    val all: List<EventResults> by lazy {
        mutableListOf<EventResults>().apply {
            addAll(overallEventResults)
            add(classEventResults)
            add(topTimesEventResults)
            add(individualEventResults)
        }
    }
}