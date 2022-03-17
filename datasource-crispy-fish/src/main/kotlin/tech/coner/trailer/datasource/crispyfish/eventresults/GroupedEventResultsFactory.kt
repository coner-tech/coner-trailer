package tech.coner.trailer.datasource.crispyfish.eventresults

import tech.coner.trailer.Class
import tech.coner.trailer.Event
import tech.coner.trailer.Participant
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.eventresults.GroupEventResults
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.eventresults.StandardEventResultsTypes

class GroupedEventResultsFactory(
    private val groupParticipantResultMapper: ParticipantResultMapper,
    private val rawTimeParticipantResultMapper: ParticipantResultMapper,
    private val scoredRunsComparatorProvider: (Int) -> ParticipantResult.ScoredRunsComparator
) {

    fun factory(
        eventCrispyFishMetadata: Event.CrispyFishMetadata,
        allClassesByAbbreviation: Map<String, Class>,
        context: CrispyFishEventMappingContext
    ) : GroupEventResults {
        val scoredRunsComparator = scoredRunsComparatorProvider(context.runCount)
        val groupResults = context.allRegistrations
            .mapNotNull { registration ->
                groupParticipantResultMapper.toCore(
                    eventCrispyFishMetadata = eventCrispyFishMetadata,
                    context = context,
                    allClassesByAbbreviation = allClassesByAbbreviation,
                    cfRegistration = registration
                )
            }
        val rawResults = context.allRegistrations
            .mapNotNull { registration ->
                rawTimeParticipantResultMapper.toCore(
                    eventCrispyFishMetadata = eventCrispyFishMetadata,
                    context = context,
                    allClassesByAbbreviation = allClassesByAbbreviation,
                    cfRegistration = registration
                )
            }
            .sortedWith(compareBy(ParticipantResult::score).then(scoredRunsComparator))
        val groupParentTopTimes = rawResults
            .groupBy { it.participant.classing?.group?.parent }
            .mapNotNull { (parent, participantResults) ->
                if (parent == null) {
                    return@mapNotNull null
                }
                val topParticipantResult = participantResults.firstOrNull()
                    ?.let { rawTimeParticipantResultMapper.toCoreRanked(participantResults, 0, it) }
                    ?: return@mapNotNull null
                GroupEventResults.ParentClassTopTime(parent, topParticipantResult)
            }
        val handicapParentTopTimes = rawResults
            .groupBy { it.participant.classing?.handicap?.parent }
            .mapNotNull { (parent, participantResults) ->
                if (parent == null) {
                    return@mapNotNull null
                }
                val topParticipantResult = participantResults.firstOrNull()
                    ?.let { rawTimeParticipantResultMapper.toCoreRanked(participantResults, 0, it) }
                    ?: return@mapNotNull null
                GroupEventResults.ParentClassTopTime(parent, topParticipantResult)
            }
        return GroupEventResults(
            type = StandardEventResultsTypes.clazz,
            runCount = context.runCount,
            groupParticipantResults = groupResults
                .sortedWith(compareBy(ParticipantResult::score).then(scoredRunsComparator))
                .groupBy { it.participant.resultGroup() }
                .mapNotNull { (grouping, results) -> grouping?.let { groupingNotNull ->
                    groupingNotNull to results.mapIndexed { index, result ->
                        groupParticipantResultMapper.toCoreRanked(
                            sortedResults = results,
                            index = index,
                            result = result
                        )
                    }
                } }
                .toMap()
                .toSortedMap(),
            parentClassTopTimes = mutableListOf<GroupEventResults.ParentClassTopTime>()
                .apply {
                    addAll(handicapParentTopTimes)
                    addAll(groupParentTopTimes)
                }
                .distinct()
                .sortedWith(compareBy<GroupEventResults.ParentClassTopTime> { it.participantResult.score }.thenBy { it.parent.sort })
        )
    }

    private fun Participant.resultGroup(): Class? {
        return classing?.group
            ?: classing?.handicap
    }

}