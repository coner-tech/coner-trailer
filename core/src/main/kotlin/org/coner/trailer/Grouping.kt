package org.coner.trailer

import java.util.*

sealed class Grouping(
        val abbreviation: String,
        val name: String,
        val sort: Int?
) : Comparable<Grouping> {
    class Singular(
            abbreviation: String,
            name: String,
            sort: Int
    ) : Grouping(
            abbreviation = abbreviation,
            name = name,
            sort = sort
    )

    class Paired(
            val pair: Pair<Grouping, Grouping>,
            groupingsAsList: List<Grouping?> = pair.toList()
    ) : Grouping(
            abbreviation = groupingsAsList.joinToString(separator = " ") { it?.abbreviation ?: "" }.trim(),
            name = groupingsAsList.joinToString(separator = ", ") { it?.name ?: "" }.trim(),
            sort = pair.first.sort
    )

    companion object {
        val UNKNOWN = Singular(
            abbreviation = "UNKNOWN",
            name = "Unknown",
            sort = Integer.MIN_VALUE
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