package org.coner.trailer

import java.math.BigDecimal

data class Time(
        val value: BigDecimal
) {
    constructor(seconds: Int, milliseconds: Int) : this(BigDecimal("$seconds.$milliseconds"))
    constructor(string: String) : this(BigDecimal(string))

    init {
        check(value.scale() == 3) { "There must be 3 decimal digits, but there were ${value.scale()}" }
    }
}