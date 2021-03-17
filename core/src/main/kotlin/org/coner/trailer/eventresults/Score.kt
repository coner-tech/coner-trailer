package org.coner.trailer.eventresults

import org.coner.trailer.Policy
import org.coner.trailer.Time
import java.math.BigDecimal

data class Score constructor(
        val value: BigDecimal,
        val penalty: Penalty? = null
) : Comparable<Score> {

    constructor(
            value: String,
            penalty: Penalty? = null
    ) : this(value = BigDecimal(value), penalty = penalty)

    init {
        require(value.scale() == 3) {
            "Scale must be 3 but was ${value.scale()}"
        }
        require(penalty == null || value >= penalty.floor) {
            "Score with penalty must have value greater than or equal to its penalty's floor. Use the withPenalty factory."
        }
    }

    companion object {

        fun withPenalty(time: Time, penalty: Penalty): Score {
            return Score(value = penalty.floor + time.value, penalty = penalty)
        }

        fun withoutTime() = Score(value = BigDecimal.valueOf(Penalty.intMaxValueTwoTenthsAsLong).setScale(3))

        fun clean(time: Time): Score {
            return Score(value = time.value)
        }
    }

    sealed class Penalty(
            val floor: BigDecimal
    ) {
        object DidNotFinish : Penalty(floor = BigDecimal.valueOf(intMaxValueOneTenthAsLong).setScale(3))
        object Disqualified : Penalty(floor = BigDecimal.valueOf(intMaxValueTwoTenthsAsLong).setScale(3))
        object Unknown : Penalty(floor = BigDecimal.valueOf(intMaxValueNineTenthsAsLong).setScale(3))
        class Cone(policy: Policy, val count: Int) : Penalty(
            floor = BigDecimal(policy.conePenaltySeconds).multiply(BigDecimal(count)).setScale(3)
        )
        companion object {
            const val intMaxValueOneTenthAsLong = 214748364L
            const val intMaxValueTwoTenthsAsLong = 429496729L
            const val intMaxValueNineTenthsAsLong = 1932735276L
        }

    }

    override fun compareTo(other: Score): Int {
        return value.compareTo(other.value)
    }
}
