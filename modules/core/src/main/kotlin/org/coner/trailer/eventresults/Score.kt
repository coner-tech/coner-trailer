package org.coner.trailer.eventresults

import org.coner.trailer.Time
import java.math.BigDecimal

data class Score constructor(
        val value: BigDecimal,
        val penalty: Penalty? = null
) {

    companion object {

        fun withPenalty(time: Time, penalty: Penalty): Score {
            val timeScoreInt = penalty.floor + (time.value.toScoreInt())
            return Score(value = BigDecimal("$timeScoreInt.000"), penalty = penalty)
        }

        fun withoutTime() = Score(value = BigDecimal("${INT_MAX_VALUE_TWO_TENTHS}.000"))
    }

    enum class Penalty(
            val floor: Int
    ) {
        DID_NOT_FINISH(INT_MAX_VALUE_ONE_TENTH),
        DISQUALIFIED(INT_MAX_VALUE_TWO_TENTHS)
    }

}

private const val INT_MAX_VALUE_ONE_TENTH = 214748364
private const val INT_MAX_VALUE_TWO_TENTHS = 429496729
private const val INT_MAX_VALUE_THREE_TENTHS = 644245094

private fun BigDecimal.toScoreInt(): Int {
    check(toInt() < INT_MAX_VALUE_ONE_TENTH) { "Integer value for scoring must be less than $INT_MAX_VALUE_ONE_TENTH" }
    check(scale() == 3) { "Scale must be 3, but was: ${scale()}" }
    return unscaledValue().toInt()
}
