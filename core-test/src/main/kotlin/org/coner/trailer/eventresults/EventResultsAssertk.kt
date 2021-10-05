package org.coner.trailer.eventresults

import assertk.Assert
import assertk.all
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.prop

fun Assert<EventResults>.type() = prop("type") { it.type }
fun Assert<EventResults>.hasType(expected: EventResultsType) = type().isEqualTo(expected)

fun Assert<EventResults>.runCount() = prop("runCount") { it.runCount }
fun Assert<EventResults>.hasRunCount(expected: Int) = runCount().isEqualTo(expected)

fun Assert<List<ParticipantResult>>.positionDistinct(position: Int) = transform {
    it.singleOrNull { it.position == position }
}
fun Assert<List<ParticipantResult>>.positionDistinct(position: Int, body: Assert<ParticipantResult>.() -> Unit) = positionDistinct(position)
        .isNotNull()
        .all(body)
