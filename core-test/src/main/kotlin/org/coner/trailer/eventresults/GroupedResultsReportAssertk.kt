package org.coner.trailer.eventresults

import assertk.Assert
import assertk.assertions.prop

fun Assert<GroupedResultsReport>.groupingsToResultsMap() = prop("groupingsToResultsMap") { it.groupingsToResultsMap }
fun Assert<GroupedResultsReport>.resultsForGroupingAbbreviation(abbreviation: String) = groupingsToResultsMap()
        .transform("results for grouping with abbreviation $abbreviation") { map ->
            map.keys.singleOrNull { grouping -> abbreviation == grouping.abbreviation }?.let { key ->
                map[key]
            }
        }
