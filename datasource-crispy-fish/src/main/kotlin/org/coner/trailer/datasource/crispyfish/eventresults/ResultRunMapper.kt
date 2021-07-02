package org.coner.trailer.datasource.crispyfish.eventresults

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
        participantCfRuns: List<Pair<Int, Run>>,
        participant: Participant
    ): List<ResultRun> {
        var participantResultRunIndex = 0
        val cores = mutableListOf<ResultRun>()
        for ((cfRunIndex, participantCfRun) in participantCfRuns) {
            val core = toCore(
                context = context,
                cfRun = participantCfRun,
                cfRunIndex = cfRunIndex,
                participantResultRunIndex = participantResultRunIndex,
                participant = participant
            )
            if (core != null) {
                cores += core
                participantResultRunIndex++
            }
        }
        return cores
    }
}
