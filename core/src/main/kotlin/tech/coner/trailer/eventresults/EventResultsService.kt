package tech.coner.trailer.eventresults

import tech.coner.trailer.Event

@Deprecated(message = "Preparing to remove in favor of EventResultsCalculator")
interface EventResultsService {

    fun buildRawResults(event: Event): OverallEventResults

    fun buildPaxResults(event: Event): OverallEventResults

    fun buildOverallTypeResults(event: Event, type: EventResultsType): OverallEventResults

    fun buildClassResults(event: Event): ClazzEventResults

    fun buildGroupTypeResults(event: Event, type: EventResultsType): ClazzEventResults

}