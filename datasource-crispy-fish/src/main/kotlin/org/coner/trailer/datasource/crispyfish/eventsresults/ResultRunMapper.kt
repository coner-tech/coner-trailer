package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.trailer.Participant
import org.coner.trailer.Time
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.eventresults.ResultRun
import tech.coner.crispyfish.model.PenaltyType
import tech.coner.crispyfish.model.RegistrationRun
import tech.coner.crispyfish.model.Run

class ResultRunMapper(
    private val scoreMapper: ScoreMapper
) {

    fun toCore(
        cfRun: Run,
        cfRunIndex: Int,
        participant: Participant,
        score: Boolean
    ): ResultRun? {
        return ResultRun(
            sequence = cfRunIndex + 1,
            score = if (score) {
                scoreMapper.toScore(
                    cfRun = cfRun,
                    participant = participant
                ) ?: return null
            } else {
                null
            },
            cones = if (cfRun.penaltyType == PenaltyType.CONE) cfRun.cones else null,
            didNotFinish = cfRun.penaltyType == PenaltyType.DID_NOT_FINISH,
            disqualified = cfRun.penaltyType == PenaltyType.DISQUALIFIED,
            rerun = cfRun.penaltyType == PenaltyType.RERUN,
            time = cfRun.timeScratchAsString?.let { Time(it) }
        )
    }

    fun toCore(
        context: CrispyFishEventMappingContext,
        participantCfRuns: List<Run>,
        participant: Participant
    ): List<ResultRun> {
        TODO("consume and score eligible runs")
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
