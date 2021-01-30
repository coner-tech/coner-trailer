package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.Registration
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.eventresults.OverallResultsReport
import org.coner.trailer.eventresults.StandardResultsTypes

class OverallRawTimeResultsReportCreator(
    private val participantResultMapper: ParticipantResultMapper
) {

    fun createFromRegistrationData(
        context: CrispyFishEventMappingContext
    ) : OverallResultsReport {
        val results = context.allRegistrations
            .mapNotNull {
                participantResultMapper.toCore(
                    context = context,
                    cfRegistration = it,
                    cfResult = it.rawResult
                )
            }
            .sortedBy { it.position }
        return OverallResultsReport(
            type = StandardResultsTypes.overallRawTime,
            participantResults = results
        )
    }
}