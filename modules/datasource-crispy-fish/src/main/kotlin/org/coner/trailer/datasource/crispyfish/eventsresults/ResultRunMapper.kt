package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.RegistrationRun
import org.coner.trailer.Time
import org.coner.trailer.eventresults.ResultRun

object ResultRunMapper {

    fun map(
            crispyFishRegistrationRun: RegistrationRun,
            crispyFishRegistrationRunIndex: Int,
            crispyFishRegistrationBestRun: Int?
    ): ResultRun {
        return ResultRun(
                time = crispyFishRegistrationRun.mapTime(),
                cones = crispyFishRegistrationRun.mapCones(),
                didNotFinish = crispyFishRegistrationRun.mapDidNotFinish(),
                disqualified = crispyFishRegistrationRun.mapDisqualified(),
                rerun = false, // no re-runs reported from crispy fish registration result
                personalBest = crispyFishRegistrationRunIndex + 1 == crispyFishRegistrationBestRun
        )
    }

    private fun RegistrationRun.mapTime(): Time? {
        return if (hasValidTime())
            Time(time)
        else
            null
    }

    private fun RegistrationRun.hasValidTime(): Boolean {
        return Time.pattern.matcher(this.time).matches()
    }

    private fun RegistrationRun.mapCones(): Int? {
        val penalty = penalty
        return if (penalty is RegistrationRun.Penalty.Cone) {
            penalty.count
        } else {
            null
        }
    }

    private fun RegistrationRun.mapDidNotFinish(): Boolean {
        val penalty = penalty
        return penalty is RegistrationRun.Penalty.DidNotFinish
    }

    private fun RegistrationRun.mapDisqualified(): Boolean {
        return penalty is RegistrationRun.Penalty.Disqualified
    }


}
