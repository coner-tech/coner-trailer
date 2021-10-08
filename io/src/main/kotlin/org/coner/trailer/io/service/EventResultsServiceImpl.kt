package org.coner.trailer.io.service

import org.coner.trailer.Event
import org.coner.trailer.Policy
import org.coner.trailer.eventresults.*

class EventResultsServiceImpl(
    private val crispyFishEventResultsService: CrispyFishEventResultsServiceImpl
) : EventResultsService {

    override fun buildRawResults(event: Event): OverallEventResults {
        return buildOverallTypeResults(event, StandardEventResultsTypes.raw)
    }

    override fun buildPaxResults(event: Event): OverallEventResults {
        return buildOverallTypeResults(event, StandardEventResultsTypes.pax)
    }

    override fun buildOverallTypeResults(event: Event, type: EventResultsType): OverallEventResults {
        return when (event.policy.authoritativeRunSource) {
            Policy.RunSource.CrispyFish -> when (type) {
                StandardEventResultsTypes.raw -> crispyFishEventResultsService.buildRawResults(event)
                StandardEventResultsTypes.pax -> crispyFishEventResultsService.buildPaxResults(event)
                else -> throw IllegalArgumentException()
            }
        }
    }

    override fun buildClassResults(event: Event): GroupEventResults {
        return buildGroupTypeResults(event, StandardEventResultsTypes.clazz)
    }

    override fun buildGroupTypeResults(event: Event, type: EventResultsType): GroupEventResults {
        return when (event.policy.authoritativeRunSource) {
            Policy.RunSource.CrispyFish -> when (type) {
                StandardEventResultsTypes.clazz -> crispyFishEventResultsService.buildClassResults(event)
                else -> throw IllegalArgumentException()
            }
        }
    }

    override fun buildIndividualResults(event: Event): IndividualEventResults {
        return when (event.policy.authoritativeRunSource) {
            Policy.RunSource.CrispyFish -> crispyFishEventResultsService.buildIndividualResults(event)
        }
    }
}