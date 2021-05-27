package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.trailer.Participant
import org.coner.trailer.Time
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishRunMapper
import org.coner.trailer.eventresults.ResultRun
import tech.coner.crispyfish.model.PenaltyType
import tech.coner.crispyfish.model.RegistrationRun
import tech.coner.crispyfish.model.Run

class ResultRunMapper(
    private val cfRunMapper: CrispyFishRunMapper,
    private val scoreMapper: ScoreMapper
) {

    fun toCore(
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

    private fun mapTime(run: RegistrationRun): Time? {
        val runTime = run.time
        return if (runTime != null && Time.pattern.matcher(runTime).matches()) {
            Time(runTime)
        } else {
            null
        }
    }
}
