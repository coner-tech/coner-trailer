package tech.coner.trailer.datasource.crispyfish.eventresults

import tech.coner.trailer.Class
import tech.coner.trailer.Event
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.eventresults.StandardEventResultsTypes

class OverallRawEventResultsFactory(
    private val participantResultMapper: ParticipantResultMapper,
    private val scoredRunsComparatorProvider: (Int) -> ParticipantResult.ScoredRunsComparator
) : CrispyFishOverallEventResultsFactory {

    override fun factory(
        eventCrispyFishMetadata: Event.CrispyFishMetadata,
        allClassesByAbbreviation: Map<String, Class>,
        context: CrispyFishEventMappingContext
    ) : OverallEventResults {
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
            .sortedWith(compareBy(ParticipantResult::score).then(scoredRunsComparator))
        return OverallEventResults(
            type = StandardEventResultsTypes.raw,
            runCount = context.runCount,
            participantResults = results.mapIndexed { index, result ->
                participantResultMapper.toCoreRanked(
                    sortedResults = results,
                    index = index,
                    result = result
                )
            }
        )
    }
}