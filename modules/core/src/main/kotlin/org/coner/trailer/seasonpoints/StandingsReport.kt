package org.coner.trailer.seasonpoints

import org.coner.trailer.Person
import org.coner.trailer.SeasonEvent
import java.util.*

class StandingsReport(
        val sections: List<Section>,
        val seasonEvents: List<SeasonEvent>
) {
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
    )

}