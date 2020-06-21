package org.coner.trailer.seasonpoints

import org.coner.trailer.Person
import org.coner.trailer.SeasonEvent
import org.coner.trailer.Time
import org.coner.trailer.average

class PersonStandingAccumulator(
        val person: Person
) {
    val eventToPoints = mutableMapOf<SeasonEvent, Int>()
    val positionToFinishCount = mutableMapOf<Int, Int>()
    var score: Int = 0
    val marginsOfVictory = mutableListOf<Time>()
    var position: Int? = null
    var tie: Boolean = false
}

fun Comparator<PersonStandingAccumulator>.thenByPositionFinishCountDescending(position: Int): Comparator<PersonStandingAccumulator> {
    return thenByDescending { it.positionToFinishCount[position] ?: 0 }
}

fun Comparator<PersonStandingAccumulator>.thenByAverageMarginOfVictoryDescending(): Comparator<PersonStandingAccumulator> {
    return thenByDescending { it.marginsOfVictory.average() }
}
