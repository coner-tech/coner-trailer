package tech.coner.trailer.io.service

import tech.coner.trailer.Event
import tech.coner.trailer.Policy
import tech.coner.trailer.datasource.crispyfish.eventresults.CrispyFishOverallEventResultsFactory
import tech.coner.trailer.datasource.crispyfish.eventresults.GroupedEventResultsFactory
import tech.coner.trailer.eventresults.*

class CrispyFishEventResultsServiceImpl(
    private val crispyFishClassService: CrispyFishClassService,
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService,
    private val overallRawEventResultsFactory: (Policy) -> CrispyFishOverallEventResultsFactory,
    private val overallPaxEventResultsFactory: (Policy) -> CrispyFishOverallEventResultsFactory,
    private val groupEventResultsFactory: (Policy) -> GroupedEventResultsFactory
) : EventResultsService {

    override fun buildRawResults(event: Event): OverallEventResults {
        return buildOverallTypeResults(event, StandardEventResultsTypes.raw)
    }

    override fun buildPaxResults(event: Event): OverallEventResults {
        return buildOverallTypeResults(event, StandardEventResultsTypes.pax)
    }

    override fun buildOverallTypeResults(event: Event, type: EventResultsType): OverallEventResults {
        val eventCrispyFish = event.requireCrispyFish()
        val factory = when (type) {
            StandardEventResultsTypes.raw -> overallRawEventResultsFactory(event.policy)
            StandardEventResultsTypes.pax -> overallPaxEventResultsFactory(event.policy)
            else -> throw IllegalArgumentException()
        }
        return factory.factory(
            eventCrispyFishMetadata = eventCrispyFish,
            allClassesByAbbreviation = crispyFishClassService.loadAllByAbbreviation(
                crispyFishClassDefinitionFile = eventCrispyFish.classDefinitionFile
            ),
            context = crispyFishEventMappingContextService.load(eventCrispyFish)
        )
    }

    override fun buildClassResults(event: Event): GroupEventResults {
        return buildGroupTypeResults(event, StandardEventResultsTypes.clazz)
    }

    override fun buildGroupTypeResults(event: Event, type: EventResultsType): GroupEventResults {
        val eventCrispyFish = event.requireCrispyFish()
        val factory = when (type) {
            StandardEventResultsTypes.clazz -> groupEventResultsFactory(event.policy)
            else -> throw IllegalArgumentException()
        }
        return factory.factory(
            eventCrispyFishMetadata = eventCrispyFish,
            allClassesByAbbreviation = crispyFishClassService.loadAllByAbbreviation(
                crispyFishClassDefinitionFile = eventCrispyFish.classDefinitionFile
            ),
            context = crispyFishEventMappingContextService.load(eventCrispyFish)
        )
    }
}