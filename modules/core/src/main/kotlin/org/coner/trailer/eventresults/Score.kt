package org.coner.trailer.eventresults

import org.coner.trailer.Time
import java.math.BigDecimal

data class Score constructor(
        val value: BigDecimal,
        val penalty: Penalty? = null
) {

    constructor(
            value: String,
            penalty: Penalty? = null
    ) : this(value = BigDecimal(value), penalty = penalty)

    init {
        require(value.scale() == 3) {
            "Scale must be 3 but was ${value.scale()}"
        }
        require(penalty == null || value > Penalty.floorValue) {
            "Score with penalty must have value greater than penalty floor. Use the withPenalty factory."
        }
    }

    companion object {

        fun withPenalty(time: Time, penalty: Penalty): Score {
            return Score(value = penalty.floor + time.value, penalty = penalty)
        }

        fun withoutTime() = Score(value = BigDecimal.valueOf(Penalty.intMaxValueTwoTenthsAsLong))
    }

    sealed class Penalty(
            val floor: BigDecimal
    ) {
        object DidNotFinish : Penalty(floor = BigDecimal.valueOf(intMaxValueOneTenthAsLong))
        object Disqualified : Penalty(floor = BigDecimal.valueOf(intMaxValueTwoTenthsAsLong))
        companion object {
            const val intMaxValueOneTenthAsLong = 214748364L
            const val intMaxValueTwoTenthsAsLong = 429496729L
            val floorValue: BigDecimal = BigDecimal.valueOf(intMaxValueOneTenthAsLong)
        }

    }

}
