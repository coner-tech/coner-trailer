package org.coner.trailer.eventresults

import org.coner.trailer.Grouping
import org.coner.trailer.Time
import java.math.BigDecimal
import java.math.RoundingMode

class PaxTimeRunScoreFactory(
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
        val paxTime = (scratchTime.value * (participantGrouping.paxFactor ?: BigDecimal.ONE)).setScale(3, RoundingMode.HALF_UP)
        return Score(
            value = penalty?.let { it.floor + paxTime }
                ?: paxTime,
            penalty = penalty
        )
    }
}