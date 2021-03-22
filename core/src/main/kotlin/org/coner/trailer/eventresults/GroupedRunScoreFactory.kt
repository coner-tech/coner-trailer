package org.coner.trailer.eventresults

import org.coner.trailer.Grouping
import org.coner.trailer.Time

class GroupedRunScoreFactory(
    private val rawTimes: RawTimeRunScoreFactory,
    private val paxTimes: PaxTimeRunScoreFactory
) : RunScoreFactory {
    override fun score(
        participantGrouping: Grouping,
        scratchTime: Time,
        cones: Int?,
        didNotFinish: Boolean?,
        disqualified: Boolean?
    ): Score {
        val factory = when {
            participantGrouping.paxed -> paxTimes
            else -> rawTimes
        }
        return factory.score(
            participantGrouping = participantGrouping,
            scratchTime = scratchTime,
            cones = cones,
            didNotFinish = didNotFinish,
            disqualified = disqualified
        )
    }

}