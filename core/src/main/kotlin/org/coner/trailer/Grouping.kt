package org.coner.trailer

import java.math.BigDecimal
import java.util.*

sealed class Grouping(
    val abbreviation: String,
    val name: String,
    val sort: Int?,
    val paxed: Boolean,
    val paxFactor: BigDecimal?
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
        paxFactor = pair.first.paxFactor?.multiply(pair.second.paxFactor)?.setScale(3)
            ?: pair.second.paxFactor
    )

    companion object {
        val UNKNOWN = Singular(
            abbreviation = "UNKNOWN",
            name = "Unknown",
            sort = Integer.MIN_VALUE,
            paxed = false,
            paxFactor = null
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Grouping) return false

        if (abbreviation != other.abbreviation) return false
        if (name != other.name) return false
        if (sort != other.sort) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(abbreviation, name, sort)
    }

    override fun compareTo(other: Grouping): Int {
        return compareValues(sort, other.sort)
    }


}