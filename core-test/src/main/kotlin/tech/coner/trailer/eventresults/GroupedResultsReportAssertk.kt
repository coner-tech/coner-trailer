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

fun Assert<ClazzEventResults>.parentClassTopTimes() = prop("parentClassTopTimes") { it.parentClassTopTimes }

fun Assert<ClazzEventResults.ParentClassTopTime>.parent() = prop("parent") { it.parent }
fun Assert<ClazzEventResults.ParentClassTopTime>.participantResult() = prop("participantResult") { it.participantResult }