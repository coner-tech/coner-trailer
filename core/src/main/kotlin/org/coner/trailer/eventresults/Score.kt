package org.coner.trailer.eventresults

import org.coner.trailer.Time
import java.math.BigDecimal
import java.math.RoundingMode

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
            "Score with penalty must have value greater than or equal to its penalty's floor."
        }
    }

    sealed class Penalty(
        val floor: BigDecimal
    ) {
        object DidNotFinish : Penalty(floor = BigDecimal.valueOf(intMaxValueOneTenthAsLong).setScale(3))
        object Disqualified : Penalty(floor = BigDecimal.valueOf(intMaxValueTwoTenthsAsLong).setScale(3))
        object Unknown : Penalty(floor = BigDecimal.valueOf(intMaxValueNineTenthsAsLong).setScale(3))
        class Cone(floor: BigDecimal, val count: Int) : Penalty(floor = floor)

        companion object {
            const val intMaxValueOneTenthAsLong = 214748364L
            const val intMaxValueTwoTenthsAsLong = 429496729L
            const val intMaxValueNineTenthsAsLong = 1932735276L
        }

    }

    override fun compareTo(other: Score): Int {
        return value.compareTo(other.value)
    }

    class PaxTimeFactory {
        fun withPenalty(scratchTime: Time, paxFactor: BigDecimal, penalty: Penalty): Score {
            return Score(value = penalty.floor + scratchTime.paxed(paxFactor), penalty = penalty)
        }

        fun withPenalty(scratchTime: String, paxFactor: BigDecimal, penalty: Penalty): Score = withPenalty(Time(scratchTime), paxFactor, penalty)

        fun withoutTime() = Score(value = BigDecimal.valueOf(Penalty.intMaxValueTwoTenthsAsLong).setScale(3))

        fun clean(scratchTime: Time, paxFactor: BigDecimal): Score {
            return Score(value = scratchTime.paxed(paxFactor))
        }

        fun clean(scratchTime: String, paxFactor: BigDecimal) = clean(Time(scratchTime), paxFactor)

        private fun Time.paxed(paxFactor: BigDecimal): BigDecimal {
            return value.multiply(paxFactor).setScale(3, RoundingMode.HALF_UP)
        }
    }
}
