package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext

class ComprehensiveEventResultsCalculator(
    private val eventContext: EventContext,
    private val overallEventResultsCalculators: List<OverallEventResultsCalculator>,
    private val groupEventResultsCalculator: ClazzEventResultsCalculator
) : EventResultsCalculator<ComprehensiveEventResults> {

    override fun calculate(): ComprehensiveEventResults {
        return ComprehensiveEventResults(
            type = StandardEventResultsTypes.comprehensive,
            runCount = eventContext.extendedParameters.runsPerParticipant,
            overallEventResults = overallEventResultsCalculators.map { it.calculate() },
            clazzEventResults = groupEventResultsCalculator.calculate()
        )
    }
}
