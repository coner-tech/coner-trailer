package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext

class ComprehensiveEventResultsCalculator(
    private val eventContext: EventContext,
    private val overallEventResultsCalculators: List<OverallEventResultsCalculator>,
    private val groupEventResultsCalculator: ClazzEventResultsCalculator,
    private val topTimesEventResultsCalculator: TopTimesEventResultsCalculator
) : EventResultsCalculator<ComprehensiveEventResults> {

    override fun calculate(): ComprehensiveEventResults {
        return ComprehensiveEventResults(
            eventContext = eventContext,
            type = StandardEventResultsTypes.comprehensive,
            runCount = eventContext.extendedParameters.runsPerParticipant,
            overallEventResults = overallEventResultsCalculators.map { it.calculate() },
            clazzEventResults = groupEventResultsCalculator.calculate(),
            topTimesEventResults = topTimesEventResultsCalculator.calculate()
        )
    }
}
