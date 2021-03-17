package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.trailer.Event
import org.coner.trailer.Policy
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.eventresults.OverallResultsReport
import org.coner.trailer.eventresults.StandardResultsTypes

class OverallPaxTimeResultsReportCreator(
    private val participantResultMapper: ParticipantResultMapper
) : CrispyFishOverallResultsReportCreator {

    override fun createFromRegistrationData(
        corePolicy: Policy,
        eventCrispyFishMetadata: Event.CrispyFishMetadata,
        context: CrispyFishEventMappingContext
    ) : OverallResultsReport {
        val results = context.allRegistrations
            .mapNotNull { registration -> registration.paxResult?.let { paxResult ->
                participantResultMapper.toCore(
                    corePolicy = corePolicy,
                    eventCrispyFishMetadata = eventCrispyFishMetadata,
                    context = context,
                    cfRegistration = registration,
                    cfResult = paxResult,
                )
            } }
            .sortedBy { it.score }
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