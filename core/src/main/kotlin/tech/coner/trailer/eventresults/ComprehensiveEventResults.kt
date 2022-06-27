package tech.coner.trailer.eventresults

data class ComprehensiveEventResults(
    override val type: EventResultsType = StandardEventResultsTypes.comprehensive,
    override val runCount: Int,
    val overallEventResults: List<OverallEventResults>,
    val groupEventResults: List<GroupEventResults>
) : EventResults {
    val all: List<EventResults> by lazy {
        mutableListOf<EventResults>().apply {
            addAll(overallEventResults)
            addAll(groupEventResults)
        }
    }
}