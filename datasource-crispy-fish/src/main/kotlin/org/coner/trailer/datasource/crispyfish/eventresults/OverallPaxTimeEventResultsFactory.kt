package org.coner.trailer.datasource.crispyfish.eventresults

import org.coner.trailer.Class
import org.coner.trailer.Event
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.eventresults.OverallEventResults
import org.coner.trailer.eventresults.ParticipantResult
import org.coner.trailer.eventresults.StandardEventResultsTypes

class OverallPaxTimeEventResultsFactory(
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
            type = StandardEventResultsTypes.pax,
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