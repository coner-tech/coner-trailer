package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext

class ComprehensiveEventResultsCalculator(
    private val eventContext: EventContext,
    private val overallEventResultsCalculators: List<OverallEventResultsCalculator>,
    private val clazzEventResultsCalculator: ClazzEventResultsCalculator,
    private val topTimesEventResultsCalculator: TopTimesEventResultsCalculator,
    private val individualEventResultsCalculator: IndividualEventResultsCalculator
) : EventResultsCalculator<ComprehensiveEventResults> {

    override fun calculate(): ComprehensiveEventResults {
        return ComprehensiveEventResults(
            eventContext = eventContext,
            overallEventResults = overallEventResultsCalculators.map { it.calculate() },
            clazzEventResults = clazzEventResultsCalculator.calculate(),
            topTimesEventResults = topTimesEventResultsCalculator.calculate(),
            individualEventResults = individualEventResultsCalculator.calculate()
        )
    }
}
