package org.coner.trailer

import java.math.BigDecimal
import java.math.RoundingMode

sealed class Grouping(
    val abbreviation: String,
    val name: String,
    val sort: Int?,
    val paxed: Boolean,
    open val paxFactor: BigDecimal?
) : Comparable<Grouping> {
    class Singular(
        abbreviation: String,
        name: String,
        sort: Int,
        paxed: Boolean,
        paxFactor: BigDecimal?
    ) : Grouping(
        abbreviation = abbreviation,
        name = name,
        sort = sort,
        paxed = paxed,
        paxFactor = paxFactor
    )

    class Paired(
        val pair: Pair<Grouping, Grouping>,
        groupingsAsList: List<Grouping?> = pair.toList(),
    ) : Grouping(
        abbreviation = groupingsAsList.joinToString(separator = " ") { it?.abbreviation ?: "" }.trim(),
        name = groupingsAsList.joinToString(separator = ", ") { it?.name ?: "" }.trim(),
        sort = pair.first.sort,
        paxed = pair.first.paxed || pair.second.paxed,
        paxFactor = null
    ) {
        override val paxFactor by lazy {
            val firstPax = pair.first.paxFactor
            val secondPax = pair.second.paxFactor
            when {
                firstPax != null && secondPax != null -> (firstPax * secondPax).setScale(3, RoundingMode.HALF_UP)
                firstPax == null && secondPax != null -> secondPax
                firstPax != null && secondPax == null -> firstPax
                else -> null
            }
        }
    }

    companion object {
        val UNKNOWN = Singular(
            abbreviation = "UNKNOWN",
            name = "Unknown",
            sort = Integer.MIN_VALUE,
            paxed = false,
            paxFactor = null
        )
    }

    override fun compareTo(other: Grouping): Int {
        return compareValues(sort, other.sort)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Grouping

        if (abbreviation != other.abbreviation) return false
        if (name != other.name) return false
        if (sort != other.sort) return false
        if (paxed != other.paxed) return false
        if (paxFactor != other.paxFactor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = abbreviation.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (sort ?: 0)
        result = 31 * result + paxed.hashCode()
        result = 31 * result + (paxFactor?.hashCode() ?: 0)
        return result
    }


}