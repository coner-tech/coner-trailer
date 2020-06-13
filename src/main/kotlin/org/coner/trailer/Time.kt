package org.coner.trailer

import java.math.BigDecimal

class Time(
        val value: BigDecimal
) {
    constructor(valueAsString: String) : this(BigDecimal(valueAsString))

    init {
        require(value.scale() == 3) { "Value must have 3 decimal digits, but is ${value.scale()}" }
        require(value >= BigDecimal.ZERO) { "Value must be great than or equal to zero" }
    }
}