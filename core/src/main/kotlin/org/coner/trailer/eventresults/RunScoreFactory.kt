package org.coner.trailer.eventresults

import org.coner.trailer.Grouping
import org.coner.trailer.Time

interface RunScoreFactory {
    fun score(
        participantGrouping: Grouping,
        time: Time, // scratch time
        cones: Int? = null,
        didNotFinish: Boolean? = false,
        disqualified: Boolean? = false,
    ): Score
}