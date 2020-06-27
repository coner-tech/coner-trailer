package org.coner.trailer

import java.util.*

sealed class Grouping(
        val abbreviation: String,
        val name: String
) {
    class Singular(
            abbreviation: String,
            name: String
    ) : Grouping(
            abbreviation = abbreviation,
            name = name
    )

    class Paired(
            val pair: Pair<Grouping, Grouping>,
            groupingsAsList: List<Grouping> = pair.toList()
    ) : Grouping(
            abbreviation = groupingsAsList.joinToString(separator = " ") { it.abbreviation },
            name = groupingsAsList.joinToString(separator = ", ") { it.name }
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Grouping) return false

        if (abbreviation != other.abbreviation) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(abbreviation, name)
    }


}