package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext

class ComprehensiveEventResultsCalculator(
    private val overallEventResultsCalculators: List<OverallEventResultsCalculator>,
    private val groupEventResultsCalculators: List<GroupEventResultsCalculator>
) : EventResultsCalculator<ComprehensiveEventResults> {

    override fun calculate(eventContext: EventContext): ComprehensiveEventResults {
        return ComprehensiveEventResults(
            type = StandardEventResultsTypes.comprehensive,
            runCount = eventContext.extendedParameters.runsPerParticipant,
            overallEventResults = overallEventResultsCalculators.map { it.calculate(eventContext) },
            groupEventResults = groupEventResultsCalculators.map { it.calculate(eventContext) }
        )
    }
}
