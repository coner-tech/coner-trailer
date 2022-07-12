package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext

data class ComprehensiveEventResults(
    override val eventContext: EventContext,
    override val type: EventResultsType = StandardEventResultsTypes.comprehensive,
    override val runCount: Int,
    val overallEventResults: List<OverallEventResults>,
    val clazzEventResults: ClazzEventResults
) : EventResults {
    val all: List<EventResults> by lazy {
        mutableListOf<EventResults>().apply {
            addAll(overallEventResults)
            add(clazzEventResults)
        }
    }
}