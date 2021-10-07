package org.coner.trailer.io.service

import org.coner.trailer.Event
import org.coner.trailer.Policy
import org.coner.trailer.datasource.crispyfish.eventresults.OverallPaxTimeEventResultsFactory
import org.coner.trailer.datasource.crispyfish.eventresults.OverallRawEventResultsFactory
import org.coner.trailer.eventresults.EventResultsType
import org.coner.trailer.eventresults.OverallEventResults
import org.coner.trailer.eventresults.StandardEventResultsTypes

class OverallEventResultsService(
    private val crispyFishClassService: CrispyFishClassService,
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService,
    private val crispyFishOverallRawEventResultsFactory: (Policy) -> OverallRawEventResultsFactory,
    private val crispyFishOverallPaxEventResultsFactory: (Policy) -> OverallPaxTimeEventResultsFactory
) {

    fun generateRawResults(event: Event): OverallEventResults {
        return generateOverallEventResults(event, StandardEventResultsTypes.raw)
    }

    fun generatePaxResults(event: Event): OverallEventResults {
        return generateOverallEventResults(event, StandardEventResultsTypes.pax)
    }

    private fun generateOverallEventResults(event: Event, type: EventResultsType): OverallEventResults {
        return when (event.policy.authoritativeRunSource) {
            Policy.RunSource.CrispyFish -> {
                val eventCrispyFish = event.requireCrispyFish()
                val factory = when (type) {
                    StandardEventResultsTypes.raw -> crispyFishOverallRawEventResultsFactory(event.policy)
                    StandardEventResultsTypes.pax -> crispyFishOverallPaxEventResultsFactory(event.policy)
                    else -> throw IllegalArgumentException()
                }
                factory.factory(
                    eventCrispyFishMetadata = eventCrispyFish,
                    allClassesByAbbreviation = crispyFishClassService.loadAllByAbbreviation(
                        crispyFishClassDefinitionFile = eventCrispyFish.classDefinitionFile
                    ),
                    context = crispyFishEventMappingContextService.load(eventCrispyFish)
                )
            }
        }
    }
}