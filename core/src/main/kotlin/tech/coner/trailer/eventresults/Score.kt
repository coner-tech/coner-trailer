package tech.coner.trailer.eventresults

import java.math.BigDecimal

data class Score constructor(
    val value: BigDecimal,
    val penalty: Penalty? = null,
    private val strict: Boolean = true
) : Comparable<Score> {

    constructor(
        value: String,
        penalty: Penalty? = null,
        strict: Boolean = true
    ) : this(value = BigDecimal(value), penalty = penalty, strict = strict)

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

    override fun compareTo(other: Score): Int {
        return value.compareTo(other.value)
    }


    sealed class Penalty : PenaltyInterface {
        object DidNotFinish : Penalty() {
            override val diff = false
            override val floor: BigDecimal = BigDecimal.valueOf(intMaxValueOneTenthAsLong).setScale(3)
        }
        object Disqualified : Penalty() {
            override val diff = false
            override val floor: BigDecimal = BigDecimal.valueOf(intMaxValueTwoTenthsAsLong).setScale(3)
        }
        data class Cone(override val floor: BigDecimal, val count: Int) : Penalty() {
            override val diff = true

            constructor(floorAsString: String, count: Int) : this(
                floor = BigDecimal(floorAsString),
                count = count
            )
        }

        companion object {
            const val intMaxValueOneTenthAsLong = 214748364L
            const val intMaxValueTwoTenthsAsLong = 429496729L
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Penalty) return false

            if (floor != other.floor) return false
            if (diff != other.diff) return false

            return true
        }

        override fun hashCode(): Int {
            var result = floor.hashCode()
            result = 31 * result + diff.hashCode()
            return result
        }
    }

    interface PenaltyInterface {
        val floor: BigDecimal
        val diff: Boolean
    }
}
