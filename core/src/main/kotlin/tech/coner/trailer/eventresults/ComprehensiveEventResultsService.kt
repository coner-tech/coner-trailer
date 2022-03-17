package tech.coner.trailer.eventresults

import tech.coner.trailer.Event
import kotlin.reflect.full.isSubclassOf

class ComprehensiveEventResultsService(
    private val eventResultsService: EventResultsService
) {

    fun build(event: Event): ComprehensiveEventResults {
        val overallEventResults = StandardEventResultsTypes.allForIndividual
            .filter { it.clazz.isSubclassOf(OverallEventResults::class) }
            .map { eventResultsService.buildOverallTypeResults(event = event, type = it) }
        return ComprehensiveEventResults(
            runCount = overallEventResults.first().runCount,
            overallEventResults = overallEventResults,
            groupEventResults =  StandardEventResultsTypes.allForIndividual
                .filter { it.clazz.isSubclassOf(GroupEventResults::class) }
                .map { eventResultsService.buildGroupTypeResults(event = event, type = it) }
        )
    }
}