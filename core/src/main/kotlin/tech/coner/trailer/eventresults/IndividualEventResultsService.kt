package tech.coner.trailer.eventresults

import tech.coner.trailer.Event

@Deprecated("Remove")
class IndividualEventResultsService(
    private val comprehensiveEventResultsService: ComprehensiveEventResultsCalculator,
    private val individualEventResultsFactory: IndividualEventResultsCalculator
) {
    fun build(event: Event): IndividualEventResults {
        val comprehensiveEventResults = comprehensiveEventResultsService.build(event)
        return individualEventResultsFactory.factory(comprehensiveEventResults)
    }
}