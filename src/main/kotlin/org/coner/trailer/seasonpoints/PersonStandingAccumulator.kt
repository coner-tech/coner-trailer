package org.coner.trailer.seasonpoints

import org.coner.trailer.Person
import org.coner.trailer.SeasonEvent

class PersonStandingAccumulator(
        val person: Person
) {
    val eventToPoints = mutableMapOf<SeasonEvent, Int>()
    val positionToFinishCount = mutableMapOf<Int, Int>()
    var score: Int = 0
}

fun Comparator<PersonStandingAccumulator>.thenByPositionFinishCountDescending(position: Int): Comparator<PersonStandingAccumulator> {
    return thenByDescending { it.positionToFinishCount[position] ?: 0 }
}
