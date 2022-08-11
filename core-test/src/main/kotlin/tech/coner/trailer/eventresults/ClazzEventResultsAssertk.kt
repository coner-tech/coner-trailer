package tech.coner.trailer.eventresults

import assertk.Assert
import assertk.assertions.prop

fun Assert<ClazzEventResults>.groupParticipantResults() = prop("groupParticipantResults") { it.groupParticipantResults }
fun Assert<ClazzEventResults>.resultsForGroupAbbreviation(abbreviation: String) = groupParticipantResults()
    .transform("results for group with abbreviation $abbreviation") { map ->
        map.keys.singleOrNull { group -> abbreviation == group.abbreviation }?.let { key ->
            map[key]
        }
    }
