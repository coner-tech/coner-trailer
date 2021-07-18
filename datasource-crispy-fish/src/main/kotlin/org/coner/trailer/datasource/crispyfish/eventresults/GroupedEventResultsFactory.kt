package org.coner.trailer.datasource.crispyfish.eventresults

import org.coner.trailer.Class
import org.coner.trailer.Event
import org.coner.trailer.Participant
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.eventresults.GroupEventResults
import org.coner.trailer.eventresults.ParticipantResult
import org.coner.trailer.eventresults.StandardEventResultsTypes

class GroupedEventResultsFactory(
    private val participantResultMapper: ParticipantResultMapper,
    private val scoredRunsComparatorProvider: (Int) -> ParticipantResult.ScoredRunsComparator
) {

    fun factory(
        eventCrispyFishMetadata: Event.CrispyFishMetadata,
        allClassesByAbbreviation: Map<String, Class>,
        context: CrispyFishEventMappingContext
    ) : GroupEventResults {
        val scoredRunsComparator = scoredRunsComparatorProvider(context.runCount)
        val results = context.allRegistrations
            .mapNotNull { registration ->
                participantResultMapper.toCore(
                    eventCrispyFishMetadata = eventCrispyFishMetadata,
                    context = context,
                    allClassesByAbbreviation = allClassesByAbbreviation,
                    cfRegistration = registration
                )
            }
        return GroupEventResults(
            type = StandardEventResultsTypes.grouped,
            runCount = context.runCount,
            groupParticipantResults = results
                .sortedWith(compareBy(ParticipantResult::score).then(scoredRunsComparator))
                .groupBy { it.participant.resultGroup() }
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

    private fun Participant.resultGroup(): Class? {
        val group = classing?.group
        val handicap = classing?.handicap
        return when {
            group != null -> group
            handicap != null -> handicap
            else -> null
        }
    }

}