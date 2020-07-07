package org.coner.trailer

import java.math.BigDecimal

data class Score(val value: Int) {

    constructor(time: String) : this(BigDecimal(time).toScoreInt())

    companion object {

        fun forDidNotFinishWithBestTime(time: Time?): Score {
            val timeScoreInt = time?.value?.toScoreInt() ?: HALF_INT_MAX_VALUE
            return Score(HALF_INT_MAX_VALUE + timeScoreInt)
        }
    }

}

private const val HALF_INT_MAX_VALUE = Int.MAX_VALUE / 2

private fun BigDecimal.toScoreInt(): Int {
    check(toInt() < HALF_INT_MAX_VALUE) { "Integer value for scoring must be less than half Int.MAX_VALUE" }
    check(scale() == 3) { "Scale must be 3, but was: ${scale()}" }
    return unscaledValue().toInt()
}
