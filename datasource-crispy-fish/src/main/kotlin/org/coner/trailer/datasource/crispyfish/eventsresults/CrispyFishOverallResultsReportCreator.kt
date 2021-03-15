package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.trailer.Event
import org.coner.trailer.Time
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.eventresults.OverallResultsReport
import org.coner.trailer.eventresults.ParticipantResult

interface CrispyFishOverallResultsReportCreator {

    fun createFromRegistrationData(
        eventCrispyFishMetadata: Event.CrispyFishMetadata,
        context: CrispyFishEventMappingContext
    ) : OverallResultsReport

}