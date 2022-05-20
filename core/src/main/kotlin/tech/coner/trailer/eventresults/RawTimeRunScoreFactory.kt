package tech.coner.trailer.eventresults

import tech.coner.trailer.Run
import tech.coner.trailer.StandardPenaltyFactory

class RawTimeRunScoreFactory(
    private val penaltyFactory: StandardPenaltyFactory
) : RunScoreFactory {
    override fun score(run: Run): Score {
        val penalty = penaltyFactory.penalty(run)
        val scratchTime = run.requireTime()
        return Score(
            value = penalty?.let { it.floor + scratchTime.value }
                ?: scratchTime.value,
            penalty = penalty
        )
    }
}