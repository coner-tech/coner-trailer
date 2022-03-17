package tech.coner.trailer.eventresults

class ComprehensiveEventResults(
    runCount: Int,
    val overallEventResults: List<OverallEventResults>,
    val groupEventResults: List<GroupEventResults>
) : EventResults(type = StandardEventResultsTypes.comprehensive, runCount = runCount) {
    val all: List<EventResults> by lazy {
        mutableListOf<EventResults>().apply {
            addAll(overallEventResults)
            addAll(groupEventResults)
        }
    }
}