package org.coner.trailer.seasonpoints

import org.coner.trailer.Event
import org.coner.trailer.Person

class StandingsReport(
        val sections: List<Section>
) {
    class Section(
            val name: String,
            val standings: List<Standing>
    )

    class Standing(
            val position: Int,
            val person: Person,
            val eventNumberToPoints: Map<Int, Int>,
            val score: Int
    )

}