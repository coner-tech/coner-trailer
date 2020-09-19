package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.Registration
import org.coner.trailer.eventresults.OverallResultsReport
import org.coner.trailer.eventresults.StandardResultsTypes

class OverallHandicapTimeResultsReportCreator(
        private val participantResultMapper: ParticipantResultMapper
) {

    fun createFromRegistrationData(
            crispyFishRegistrations: List<Registration>
    ) : OverallResultsReport {
        val results = crispyFishRegistrations
                .mapNotNull {
                    participantResultMapper.map(
                            cfRegistration = it,
                            cfResult = it.paxResult
                    )
                }
                .sortedBy { it.position }
        return OverallResultsReport(
                type = StandardResultsTypes.overallHandicapTime,
                participantResults = results
        )
    }
}