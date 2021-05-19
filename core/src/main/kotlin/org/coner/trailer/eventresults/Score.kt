package org.coner.trailer.eventresults

import java.math.BigDecimal

data class Score constructor(
    val value: BigDecimal,
    val penalty: Penalty? = null,
    private val strict: Boolean = true
) : Comparable<Score> {

    constructor(
        value: String,
        penalty: Penalty? = null,
    ) : this(value = BigDecimal(value), penalty = penalty)

    init {
        require(value.scale() == 3) {
            "Scale must be 3 but was ${value.scale()}"
        }
        if (strict) {
            require(penalty == null || value >= penalty.floor) {
                "Score with penalty must have value greater than or equal to its penalty's floor."
            }
        }
    }

    sealed class Penalty(
        val floor: BigDecimal,
        val diff: Boolean
    ) {
        object DidNotFinish : Penalty(
            diff = false,
            floor = BigDecimal.valueOf(intMaxValueOneTenthAsLong).setScale(3)
        )
        object Disqualified : Penalty(
            diff = false,
            floor = BigDecimal.valueOf(intMaxValueTwoTenthsAsLong).setScale(3)
        )
        class Cone(floor: BigDecimal, val count: Int) : Penalty(
            diff = true,
            floor = floor
        ) {
            constructor(
                floor: String,
                count: Int
            ) : this(BigDecimal(floor), count)
        }

        companion object {
            const val intMaxValueOneTenthAsLong = 214748364L
            const val intMaxValueTwoTenthsAsLong = 429496729L
        }

    }

    override fun compareTo(other: Score): Int {
        return value.compareTo(other.value)
    }
}
