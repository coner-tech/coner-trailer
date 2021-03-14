package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.RegistrationRun
import org.coner.trailer.Time
import org.coner.trailer.eventresults.ResultRun

class ResultRunMapper {

    fun map(
            crispyFishRegistrationRun: RegistrationRun,
            crispyFishRegistrationRunIndex: Int,
            crispyFishRegistrationBestRun: Int?
    ): ResultRun {
        return ResultRun(
                time = mapTime(crispyFishRegistrationRun),
                cones = mapCones(crispyFishRegistrationRun),
                didNotFinish = mapDidNotFinish(crispyFishRegistrationRun),
                disqualified = mapDisqualified(crispyFishRegistrationRun),
                rerun = false, // no re-runs reported from crispy fish registration result
                personalBest = crispyFishRegistrationRunIndex + 1 == crispyFishRegistrationBestRun
        )
    }

    fun map(
            crispyFishRegistrationRuns: List<RegistrationRun>,
            crispyFishRegistrationBestRun: Int?
    ): List<ResultRun> {
        return crispyFishRegistrationRuns.mapIndexed { index, registrationRun ->
            map(
                    crispyFishRegistrationRun = registrationRun,
                    crispyFishRegistrationRunIndex = index,
                    crispyFishRegistrationBestRun = crispyFishRegistrationBestRun
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
