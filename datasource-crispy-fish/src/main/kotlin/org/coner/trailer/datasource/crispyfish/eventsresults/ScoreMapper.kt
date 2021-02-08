package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.crispyfish.model.Registration
import org.coner.crispyfish.model.RegistrationResult
import org.coner.crispyfish.model.RegistrationRun
import org.coner.trailer.Time
import org.coner.trailer.eventresults.ResultRun
import org.coner.trailer.eventresults.Score
import java.math.BigDecimal
import java.math.RoundingMode

object ScoreMapper {

    fun map(
            cfRegistration: Registration,
            cfResult: RegistrationResult,
            scoredRuns: List<ResultRun>
    ): Score? {
        fun synthesizePenaltyTime(penalty: Score.Penalty): Score {
            val bestRun = scoredRuns.singleOrNull { it.personalBest }
            val bestRunTime = bestRun?.time ?: return Score.withoutTime()
            val category = cfRegistration.category
            val value = when (category?.paxed) {
                true -> bestRunTime.value
                        .multiply(category.paxFactor)
                        .setScale(3, RoundingMode.HALF_UP)
                else -> bestRunTime.value
            }
            return Score.withPenalty(Time(value), penalty)
        }
        return when {
            cfResult.hasValidTime() -> Score(BigDecimal(cfResult.time))
            cfResult.hasDisqualified()
                    || cfRegistration.runs.all { it.penalty == RegistrationRun.Penalty.Disqualified } -> synthesizePenaltyTime(Score.Penalty.Disqualified)
            cfResult.hasDidNotFinish() -> synthesizePenaltyTime(Score.Penalty.DidNotFinish)
            else -> null
        }
    }

    private fun RegistrationResult.hasValidTime() = Time.pattern.matcher(time).matches()
    private fun RegistrationResult.hasDidNotFinish() = time == "DNF" || time == "-" // yes, really
    private fun RegistrationResult.hasDisqualified() = time == "DSQ"
}