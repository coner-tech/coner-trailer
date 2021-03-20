package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.trailer.Event
import org.coner.trailer.Grouping
import org.coner.trailer.Participant
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.eventresults.GroupedResultsReport
import org.coner.trailer.eventresults.StandardResultsTypes

class CompetitionGroupedResultsReportCreator(
    private val participantResultMapper: ParticipantResultMapper
) {

    fun createFromRegistrationData(
        eventCrispyFishMetadata: Event.CrispyFishMetadata,
        context: CrispyFishEventMappingContext
    ) : GroupedResultsReport {
        val results = context.allRegistrations
            .mapNotNull { registration ->
                participantResultMapper.toCore(
                    eventCrispyFishMetadata = eventCrispyFishMetadata,
                    context = context,
                    cfRegistration = registration
                )
            }
        return GroupedResultsReport(
            type = StandardResultsTypes.grouped,
            groupingsToResultsMap = results
                .sortedBy { it.score }
                .groupBy { it.participant.resultGrouping() }
                .mapNotNull { (grouping, results) -> grouping?.let { groupingNotNull ->
                    groupingNotNull to results.mapIndexed { index, result ->
                        participantResultMapper.toCoreRanked(
                            sortedResults = results,
                            index = index,
                            result = result
                        )
                    }
                } }
                .toMap()
                .toSortedMap()
        )
    }

    private fun Participant.resultGrouping(): Grouping? {
        return when (val grouping = signage?.grouping) {
            is Grouping.Singular -> grouping
            is Grouping.Paired -> grouping.pair.first
            else -> null
        }
    }

}