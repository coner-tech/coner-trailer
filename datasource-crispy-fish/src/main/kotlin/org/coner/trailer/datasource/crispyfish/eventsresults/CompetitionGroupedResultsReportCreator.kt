package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.Registration
import org.coner.trailer.Grouping
import org.coner.trailer.Participant
import org.coner.trailer.eventresults.GroupedResultsReport
import org.coner.trailer.eventresults.StandardResultsTypes

class CompetitionGroupedResultsReportCreator(
        private val participantResultMapper: ParticipantResultMapper
) {

    fun createFromRegistrationData(
            crispyFishRegistrations: List<Registration>
    ) : GroupedResultsReport {
        val results = crispyFishRegistrations
                .mapNotNull {
                    participantResultMapper.map(
                            cfRegistration = it,
                            cfResult = it.classResult
                    )
                }
        return GroupedResultsReport(
                type = StandardResultsTypes.competitionGrouped,
                groupingsToResultsMap = results
                        .sortedBy { it.score.value }
                        .groupBy { it.participant.resultGrouping() }
                        .map { (grouping, results) ->
                            grouping to results.mapIndexed { index, participantResult ->
                                participantResult.copy(
                                        position = index + 1
                                )
                            }
                        }
                        .toMap()
                        .toSortedMap()
        )
    }

    private fun Participant.resultGrouping(): Grouping {
        return when (val grouping = signage.grouping) {
            is Grouping.Singular -> grouping
            is Grouping.Paired -> grouping.pair.first
        }
    }

}