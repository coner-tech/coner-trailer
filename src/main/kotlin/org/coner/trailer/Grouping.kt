package org.coner.trailer

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
}