package org.coner.trailer.eventresults

import org.coner.trailer.Time
import java.math.BigDecimal

data class Score constructor(
        val value: BigDecimal,
        val penalty: Penalty? = null
) {

    companion object {

        fun forDidNotFinishWithBestTime(time: Time?): Score {
            val timeScoreInt = INT_MAX_VALUE_ONE_TENTH + (time?.value?.toScoreInt() ?: INT_MAX_VALUE_ONE_TENTH)
            return Score(value = BigDecimal("$timeScoreInt.000"), penalty = Penalty.DID_NOT_FINISH)
        }

        fun forDisqualifiedWithBestTime(time: Time?): Score {
            val timeScoreInt = INT_MAX_VALUE_TWO_TENTHS + (time?.value?.toScoreInt() ?: INT_MAX_VALUE_ONE_TENTH)
            return Score(value = BigDecimal("$timeScoreInt.000"), penalty = Penalty.DISQUALIFIED)
        }
    }

    enum class Penalty {
        DID_NOT_FINISH,
        DISQUALIFIED
    }

}

private const val INT_MAX_VALUE_ONE_TENTH = 214748364
private const val INT_MAX_VALUE_TWO_TENTHS = 429496729

private fun BigDecimal.toScoreInt(): Int {
    check(toInt() < INT_MAX_VALUE_ONE_TENTH) { "Integer value for scoring must be less than $INT_MAX_VALUE_ONE_TENTH" }
    check(scale() == 3) { "Scale must be 3, but was: ${scale()}" }
    return unscaledValue().toInt()
}
