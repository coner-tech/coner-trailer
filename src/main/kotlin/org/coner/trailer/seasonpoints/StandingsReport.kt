package org.coner.trailer.seasonpoints

import org.coner.trailer.Event
import org.coner.trailer.Person
import org.coner.trailer.SeasonEvent

class StandingsReport(
        val sections: List<Section>
) {
    class Section(
            val title: String,
            val standings: List<Standing>
    )

    class Standing(
            val position: Int,
            val person: Person,
            val eventToPoints: Map<SeasonEvent, Int>,
            val score: Int,
            val tie: Boolean
    )

}