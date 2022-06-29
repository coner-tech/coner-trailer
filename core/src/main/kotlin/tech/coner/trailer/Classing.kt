package tech.coner.trailer

import java.math.BigDecimal

data class Classing(
    val group: Class?,
    val handicap: Class
) : Comparable<Classing> {
    val abbreviation: String = "${group?.abbreviation ?: ""} ${handicap.abbreviation}".trim()

    val paxFactor: BigDecimal = when (group?.paxed) {
        true -> group.requirePaxFactor().multiply(handicap.requirePaxFactor())
        else -> handicap.requirePaxFactor()
    }

    override fun compareTo(other: Classing): Int {
        return comparator.compare(this, other)
    }

    companion object {
        private val comparator: Comparator<Classing> by lazy {
            compareBy<Classing> { it.group }
                .thenBy { it.handicap }
        }
    }
}