package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext

class ComprehensiveEventResultsCalculator(
    private val overallEventResultsCalculators: List<OverallEventResultsCalculator>,
    private val groupEventResultsCalculator: ClazzEventResultsCalculator
) : EventResultsCalculator<ComprehensiveEventResults> {

    override fun calculate(eventContext: EventContext): ComprehensiveEventResults {
        return ComprehensiveEventResults(
            type = StandardEventResultsTypes.comprehensive,
            runCount = eventContext.extendedParameters.runsPerParticipant,
            overallEventResults = overallEventResultsCalculators.map { it.calculate(eventContext) },
            clazzEventResults = groupEventResultsCalculator.calculate(eventContext)
        )
    }
}
