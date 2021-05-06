package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.trailer.Event
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.eventresults.OverallResultsReport
import org.coner.trailer.eventresults.ParticipantResult
import org.coner.trailer.eventresults.StandardResultsTypes

class OverallPaxTimeResultsReportCreator(
    private val participantResultMapper: ParticipantResultMapper,
    private val scoredRunsComparatorProvider: (Int) -> ParticipantResult.ScoredRunsComparator
) : CrispyFishOverallResultsReportCreator {

    override fun createFromRegistrationData(
        eventCrispyFishMetadata: Event.CrispyFishMetadata,
        context: CrispyFishEventMappingContext
    ) : OverallResultsReport {
        val scoredRunsComparator = scoredRunsComparatorProvider(context.runCount)
        val results = context.allRegistrations
            .mapNotNull { registration ->
                participantResultMapper.toCore(
                    eventCrispyFishMetadata = eventCrispyFishMetadata,
                    context = context,
                    cfRegistration = registration
                )
            }
            .sortedWith(compareBy(ParticipantResult::score).then(scoredRunsComparator))
        return OverallResultsReport(
            type = StandardResultsTypes.pax,
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