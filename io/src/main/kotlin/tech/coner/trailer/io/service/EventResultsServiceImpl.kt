package tech.coner.trailer.io.service

import tech.coner.trailer.Event
import tech.coner.trailer.Policy
import tech.coner.trailer.eventresults.*

@Deprecated(message = "Preparing to remove in favor of core module EventResultsCalculator")
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
        return when (event.policy.authoritativeRunDataSource) {
            Policy.DataSource.CrispyFish -> when (type) {
                StandardEventResultsTypes.raw -> crispyFishEventResultsService.buildRawResults(event)
                StandardEventResultsTypes.pax -> crispyFishEventResultsService.buildPaxResults(event)
                else -> throw IllegalArgumentException()
            }
        }
    }

    override fun buildClassResults(event: Event): ClazzEventResults {
        return buildGroupTypeResults(event, StandardEventResultsTypes.clazz)
    }

    override fun buildGroupTypeResults(event: Event, type: EventResultsType): ClazzEventResults {
        return when (event.policy.authoritativeRunDataSource) {
            Policy.DataSource.CrispyFish -> when (type) {
                StandardEventResultsTypes.clazz -> crispyFishEventResultsService.buildClassResults(event)
                else -> throw IllegalArgumentException()
            }
        }
    }
}