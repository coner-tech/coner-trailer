package org.coner.trailer

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.regex.Pattern

data class Time(
        val value: BigDecimal
) : Comparable<Time> {
    constructor(valueAsString: String) : this(BigDecimal(valueAsString))

    init {
        require(value.scale() == 3) { "Value must have 3 decimal digits, but is ${value.scale()}" }
        require(value >= BigDecimal.ZERO) { "Value must be great than or equal to zero" }
    }

    override fun compareTo(other: Time): Int {
        return value.compareTo(other.value)
    }

    companion object {
        val pattern: Pattern by lazy {
            Pattern.compile("^[\\d]{1,3}\\.[\\d]{3}$")
        }
    }
}

fun Collection<Time>.average(): Time? {
    var sum: BigDecimal = BigDecimal.ZERO
    var count = 0
    for (time in this) {
        sum += time.value
        count++
    }
    return when {
        count > 0 -> Time(sum.divide(BigDecimal(count), 3, RoundingMode.HALF_UP))
        else -> null
    }

}