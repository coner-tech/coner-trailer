package org.coner.trailer.datasource.crispyfish.util.bigdecimal

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.setScaleWithBuggedCrispyFishRounding(): BigDecimal {
    val roundingMode = if (scale() > 3) {
        val decimalDigits = toString().substringAfter('.')
        if (decimalDigits[3] == '9') {
            if (decimalDigits[4] == '9' || decimalDigits[4] == '8') {
                RoundingMode.CEILING
            } else {
                RoundingMode.FLOOR
            }
        } else {
            RoundingMode.FLOOR
        }
    } else {
        RoundingMode.FLOOR
    }
    return setScale(3, roundingMode)
}