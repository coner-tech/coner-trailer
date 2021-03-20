package org.coner.trailer.eventresults

import org.coner.trailer.Grouping
import org.coner.trailer.Time

class RawTimeRunScoreFactory(
    private val penaltyFactory: StandardPenaltyFactory
) : RunScoreFactory {
    override fun score(
        participantGrouping: Grouping,
        scratchTime: Time,
        cones: Int?,
        didNotFinish: Boolean?,
        disqualified: Boolean?
    ): Score {
        val penalty = penaltyFactory.penalty(
            cones = cones,
            didNotFinish = didNotFinish,
            disqualified = disqualified
        )
        return Score(
            value = penalty?.let { it.floor + scratchTime.value }
                ?: scratchTime.value,
            penalty = penalty
        )
    }
}