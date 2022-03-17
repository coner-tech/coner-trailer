package tech.coner.trailer.eventresults

import tech.coner.trailer.Event

interface EventResultsService {

    fun buildRawResults(event: Event): OverallEventResults

    fun buildPaxResults(event: Event): OverallEventResults

    fun buildOverallTypeResults(event: Event, type: EventResultsType): OverallEventResults

    fun buildClassResults(event: Event): GroupEventResults

    fun buildGroupTypeResults(event: Event, type: EventResultsType): GroupEventResults

}