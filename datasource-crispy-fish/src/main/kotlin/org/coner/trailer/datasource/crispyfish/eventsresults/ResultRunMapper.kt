package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.RegistrationRun
import org.coner.trailer.Participant
import org.coner.trailer.Time
import org.coner.trailer.eventresults.ResultRun

class ResultRunMapper(
    private val scoreMapper: ScoreMapper
) {

    fun toCore(
        cfRegistrationRun: RegistrationRun,
        cfRegistrationRunIndex: Int,
        cfRegistrationBestRun: Int?,
        participant: Participant,
    ): ResultRun? {
        return ResultRun(
            score = scoreMapper.toScore(
                cfRegistrationRun = cfRegistrationRun,
                participant = participant
            ) ?: return null,
            cones = (cfRegistrationRun.penalty as? RegistrationRun.Penalty.Cone?)?.count,
            didNotFinish = cfRegistrationRun.penalty == RegistrationRun.Penalty.DidNotFinish,
            disqualified = cfRegistrationRun.penalty == RegistrationRun.Penalty.Disqualified,
            rerun = false, // no re-runs reported in crispy fish registration file
            personalBest = cfRegistrationRunIndex + 1 == cfRegistrationBestRun,
            time = mapTime(cfRegistrationRun)
        )
    }

    fun toCore(
        cfRegistrationRuns: List<RegistrationRun>,
        cfRegistrationBestRun: Int?,
        participant: Participant,
    ): List<ResultRun> {
        return cfRegistrationRuns.mapIndexedNotNull { index, registrationRun ->
            toCore(
                cfRegistrationRun = registrationRun,
                cfRegistrationRunIndex = index,
                cfRegistrationBestRun = cfRegistrationBestRun,
                participant = participant
            )
        }
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
