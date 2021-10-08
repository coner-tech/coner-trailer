package org.coner.trailer.io.service

import org.coner.trailer.Event
import org.coner.trailer.eventresults.EventResultsType
import org.coner.trailer.eventresults.GroupEventResults
import org.coner.trailer.eventresults.IndividualEventResults
import org.coner.trailer.eventresults.OverallEventResults

interface EventResultsService {

    fun buildRawResults(event: Event): OverallEventResults

    fun buildPaxResults(event: Event): OverallEventResults

    fun buildOverallTypeResults(event: Event, type: EventResultsType): OverallEventResults

    fun buildClassResults(event: Event): GroupEventResults

    fun buildGroupTypeResults(event: Event, type: EventResultsType): GroupEventResults

    fun buildIndividualResults(event: Event): IndividualEventResults
}