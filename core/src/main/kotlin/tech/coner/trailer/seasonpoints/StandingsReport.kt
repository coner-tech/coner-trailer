package tech.coner.trailer.seasonpoints

import tech.coner.trailer.Person
import tech.coner.trailer.SeasonEvent
import java.util.*

class StandingsReport(
        val sections: List<Section>,
        val pointsEvents: List<SeasonEvent>
) {

    init {
        require(pointsEvents.all { it.points }) { "pointsEvents must contain only SeasonEvent instances with points==true" }
    }

    class Section(
            val title: String,
            val standings: List<Standing>
    )

    class Standing(
            val position: Int,
            val person: Person,
            val eventToPoints: SortedMap<SeasonEvent, Int>,
            val score: Int,
            val tie: Boolean
    ) {
        val count = eventToPoints.size
    }

}