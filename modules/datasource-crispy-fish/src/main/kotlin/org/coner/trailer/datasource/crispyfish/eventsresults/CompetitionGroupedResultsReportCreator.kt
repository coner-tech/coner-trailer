package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.Registration
import org.coner.trailer.Grouping
import org.coner.trailer.Participant
import org.coner.trailer.Person
import org.coner.trailer.eventresults.GroupedResultsReport
import org.coner.trailer.eventresults.StandardResultsTypes

class CompetitionGroupedResultsReportCreator() {

    fun createFromRegistrationData(
            crispyFishRegistrations: List<Registration>,
            peopleByMemberId: Map<String, Person>
    ) : GroupedResultsReport {
        val results = crispyFishRegistrations
                .mapNotNull {
                    ParticipantResultMapper.map(
                            crispyFishRegistration = it,
                            crispyFishResult = it.classResult,
                            peopleByMemberId = peopleByMemberId
                    )
                }
        return GroupedResultsReport(
                type = StandardResultsTypes.competitionGrouped,
                groupingsToResultsMap = results
                        .sortedBy { it.score.value }
                        .groupBy { it.participant.resultGrouping() }
        )
    }

    private fun Participant.resultGrouping(): Grouping {
        return when (val grouping = grouping) {
            is Grouping.Singular -> grouping
            is Grouping.Paired -> grouping.pair.first
        }
    }

}