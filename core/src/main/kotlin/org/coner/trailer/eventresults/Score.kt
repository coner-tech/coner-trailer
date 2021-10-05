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

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other !is Cone) return false
                if (!super.equals(other)) return false

                if (count != other.count) return false

                return true
            }

            override fun hashCode(): Int {
                var result = super.hashCode()
                result = 31 * result + count
                return result
            }
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

    override fun compareTo(other: Score): Int {
        return value.compareTo(other.value)
    }
}
