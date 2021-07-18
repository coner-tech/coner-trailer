package org.coner.trailer

import java.math.BigDecimal

data class Classing(
    val group: Class?,
    val handicap: Class
) {
    val abbreviation: String = "${group?.abbreviation ?: ""} ${handicap.abbreviation}".trim()

    val paxFactor: BigDecimal = when (group?.paxed) {
        true -> group.requirePaxFactor().multiply(handicap.requirePaxFactor())
        else -> handicap.requirePaxFactor()
    }

}