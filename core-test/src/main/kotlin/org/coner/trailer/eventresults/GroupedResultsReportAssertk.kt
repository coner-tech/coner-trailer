package org.coner.trailer.eventresults

import assertk.Assert
import assertk.assertions.prop

fun Assert<GroupedEventResults>.groupingsToResultsMap() = prop("groupingsToResultsMap") { it.groupingsToResultsMap }
fun Assert<GroupedEventResults>.resultsForGroupingAbbreviation(abbreviation: String) = groupingsToResultsMap()
        .transform("results for grouping with abbreviation $abbreviation") { map ->
            map.keys.singleOrNull { grouping -> abbreviation == grouping.abbreviation }?.let { key ->
                map[key]
            }
        }
