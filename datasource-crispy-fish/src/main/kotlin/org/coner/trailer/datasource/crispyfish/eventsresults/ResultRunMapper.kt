package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.trailer.Participant
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishRunMapper
import org.coner.trailer.eventresults.ResultRun
import org.coner.trailer.eventresults.RunEligibilityQualifier
import org.coner.trailer.eventresults.RunScoreFactory
import tech.coner.crispyfish.model.Run

class ResultRunMapper(
    private val cfRunMapper: CrispyFishRunMapper,
    private val runEligibilityQualifier: RunEligibilityQualifier,
    private val runScoreFactory: RunScoreFactory
) {

    fun toCore(
        context: CrispyFishEventMappingContext,
        cfRun: Run,
        cfRunIndex: Int,
        participantResultRunIndex: Int,
        participant: Participant,
    ): ResultRun? {
        val run = cfRunMapper.toCore(
            cfRun = cfRun,
            cfRunIndex = cfRunIndex,
            participant = participant
        )
        if (
            !runEligibilityQualifier.check(
                run = run,
                participantResultRunIndex = participantResultRunIndex,
                maxRunCount = context.runCount
            )
        ) {
            return null
        }
        return ResultRun(
            run = run,
            score = runScoreFactory.score(run)
        )
    }


    fun toCores(
        context: CrispyFishEventMappingContext,
        participantCfRuns: List<Run>,
        participant: Participant
    ): List<ResultRun> {
        return participantCfRuns.mapIndexedNotNull { index, run ->
            val cfRunIndex = run.number?.minus(1)
                ?: return@mapIndexedNotNull null
            toCore(
                context = context,
                cfRun = run,
                cfRunIndex = cfRunIndex,
                participantResultRunIndex = index,
                participant = participant
            )
        }
    }
}
