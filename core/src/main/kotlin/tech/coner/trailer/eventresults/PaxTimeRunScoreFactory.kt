package tech.coner.trailer.eventresults

import tech.coner.trailer.Run
import tech.coner.trailer.StandardPenaltyFactory
import java.math.BigDecimal
import java.math.RoundingMode

open class PaxTimeRunScoreFactory(
    private val penaltyFactory: StandardPenaltyFactory
) : RunScoreFactory {
    override fun score(run: Run): Score {
        val participantClassing = run.requireParticipantClassing()
        val scratchTime = run.requireTime()
        val penalty = penaltyFactory.penalty(run)
        val paxTime = (scratchTime.value * (participantClassing.paxFactor ?: BigDecimal.ONE)).setScale(3, RoundingMode.HALF_UP)
        return Score(
            value = penalty?.let { it.floor + paxTime }
                ?: paxTime,
            penalty = penalty
        )
    }
}