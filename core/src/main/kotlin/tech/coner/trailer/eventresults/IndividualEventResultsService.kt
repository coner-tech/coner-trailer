package tech.coner.trailer.eventresults

import tech.coner.trailer.Event

class IndividualEventResultsService(
    private val comprehensiveEventResultsService: ComprehensiveEventResultsService,
    private val individualEventResultsFactory: IndividualEventResultsFactory
) {
    fun build(event: Event): IndividualEventResults {
        val comprehensiveEventResults = comprehensiveEventResultsService.build(event)
        return individualEventResultsFactory.factory(comprehensiveEventResults)
    }
}