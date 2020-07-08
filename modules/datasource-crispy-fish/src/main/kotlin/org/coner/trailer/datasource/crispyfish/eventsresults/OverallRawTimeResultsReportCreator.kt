package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.Registration
import org.coner.trailer.Person
import org.coner.trailer.eventresults.OverallResultsReport
import org.coner.trailer.eventresults.StandardResultsTypes

class OverallRawTimeResultsReportCreator {

    fun createFromRegistrationData(
            crispyFishRegistrations: List<Registration>,
            peopleByMemberId: Map<String, Person>
    ) : OverallResultsReport {
        val results = crispyFishRegistrations
                .mapNotNull {
                    ParticipantResultMapper.map(
                            cfRegistration = it,
                            cfResult = it.rawResult,
                            peopleByMemberId = peopleByMemberId
                    )
                }
                .sortedBy { it.position }
        return OverallResultsReport(
                type = StandardResultsTypes.overallRawTime,
                participantResults = results
        )
    }
}