package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.RegistrationRun
import org.coner.trailer.Time
import org.coner.trailer.eventresults.ResultRun
import org.coner.trailer.Policy

class ResultRunMapper(
    private val scoreMapper: ScoreMapper
) {

    fun toCore(
        cfRegistrationRun: RegistrationRun,
        cfRegistrationRunIndex: Int,
        cfRegistrationBestRun: Int?,
        corePolicy: Policy
    ): ResultRun {
        return ResultRun(
            time = mapTime(cfRegistrationRun),
            cones = mapCones(cfRegistrationRun),
            didNotFinish = mapDidNotFinish(cfRegistrationRun),
            disqualified = mapDisqualified(cfRegistrationRun),
            rerun = false, // no re-runs reported in crispy fish registration file
            personalBest = cfRegistrationRunIndex + 1 == cfRegistrationBestRun,
            score = scoreMapper.toScore(
                cfRegistrationRun = cfRegistrationRun,
                corePolicy = corePolicy
            )
        )
    }

    fun toCore(
        crispyFishRegistrationRuns: List<RegistrationRun>,
        crispyFishRegistrationBestRun: Int?,
        corePolicy: Policy
    ): List<ResultRun> {
        return crispyFishRegistrationRuns.mapIndexed { index, registrationRun ->
            toCore(
                cfRegistrationRun = registrationRun,
                cfRegistrationRunIndex = index,
                cfRegistrationBestRun = crispyFishRegistrationBestRun,
                corePolicy = corePolicy
            )
        }
    }

    private fun mapTime(run: RegistrationRun): Time? {
        return if (hasValidTime(run)) {
            Time(run.time ?: return null)
        } else {
            null
        }
    }

    private fun hasValidTime(run: RegistrationRun): Boolean {
        return run.time?.let { Time.pattern.matcher(it).matches() } ?: false
    }

    private fun mapCones(run: RegistrationRun): Int? {
        val penalty = run.penalty
        return if (penalty is RegistrationRun.Penalty.Cone) {
            penalty.count
        } else {
            null
        }
    }

    private fun mapDidNotFinish(run: RegistrationRun): Boolean {
        val penalty = run.penalty
        return penalty is RegistrationRun.Penalty.DidNotFinish
    }

    private fun mapDisqualified(run: RegistrationRun): Boolean {
        return run.penalty is RegistrationRun.Penalty.Disqualified
    }


}
