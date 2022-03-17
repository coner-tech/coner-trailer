package tech.coner.trailer.seasonpoints

import tech.coner.trailer.Person
import tech.coner.trailer.SeasonEvent

class PersonStandingAccumulator(
        val person: Person
) {
    val eventToPoints = mutableMapOf<SeasonEvent, Int>()
    val positionToFinishCount = mutableMapOf<Int, Int>()
    var score: Int = 0
    var position: Int? = null
    var tie: Boolean = false
}
