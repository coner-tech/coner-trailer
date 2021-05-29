package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.trailer.Participant
import org.coner.trailer.Time
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishRunMapper
import org.coner.trailer.eventresults.ResultRun
import org.coner.trailer.eventresults.RunEligibilityQualifier
import tech.coner.crispyfish.model.PenaltyType
import tech.coner.crispyfish.model.RegistrationRun
import tech.coner.crispyfish.model.Run

class ResultRunMapper(
    private val cfRunMapper: CrispyFishRunMapper,
    private val runEligibilityQualifier: RunEligibilityQualifier,
    private val scoreMapper: ScoreMapper
) {

    fun toCore(
        context: CrispyFishEventMappingContext,
        cfRun: Run,
        cfRunIndex: Int,
        participant: Participant,
    ): ResultRun? {
        return ResultRun(
            run = cfRunMapper.toCore(
                cfRun = cfRun,
                cfRunIndex = cfRunIndex,
                participant = participant
            ),
            score = scoreMapper.toScore(
                cfRun = cfRun,
                participant = participant
            ) ?: return null
        )
    }


    fun toCores(
        context: CrispyFishEventMappingContext,
        participantCfRuns: List<Run>,
        participant: Participant
    ): List<ResultRun> {

    }

    private fun mapTime(run: RegistrationRun): Time? {
        val runTime = run.time
        return if (runTime != null && Time.pattern.matcher(runTime).matches()) {
            Time(runTime)
        } else {
            null
        }
    }
}
