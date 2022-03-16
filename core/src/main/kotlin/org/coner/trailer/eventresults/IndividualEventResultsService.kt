package org.coner.trailer.eventresults

import org.coner.trailer.Event

class IndividualEventResultsService(
    private val comprehensiveEventResultsService: ComprehensiveEventResultsService,
    private val individualEventResultsFactory: IndividualEventResultsFactory
) {
    fun build(event: Event): IndividualEventResults {
        val comprehensiveEventResults = comprehensiveEventResultsService.build(event)
        return individualEventResultsFactory.factory(comprehensiveEventResults)
    }
}