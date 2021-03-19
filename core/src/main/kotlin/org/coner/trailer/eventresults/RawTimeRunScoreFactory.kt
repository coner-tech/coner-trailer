package org.coner.trailer.eventresults

import org.coner.trailer.Grouping
import org.coner.trailer.Policy
import org.coner.trailer.Time

class RawTimeRunScoreFactory(
    private val policy: Policy
) : RunScoreFactory {
    override fun score(
        participantGrouping: Grouping,
        time: Time,
        cones: Int?,
        didNotFinish: Boolean?,
        disqualified: Boolean?
    ): Score {
        TODO("Not yet implemented")
    }
}