package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.trailer.Grouping
import org.coner.trailer.Run
import org.coner.trailer.Time
import org.coner.trailer.datasource.crispyfish.util.bigdecimal.setScaleWithBuggedCrispyFishRounding
import org.coner.trailer.eventresults.PaxTimeRunScoreFactory
import org.coner.trailer.eventresults.Score
import org.coner.trailer.eventresults.StandardPenaltyFactory

/**
 * A PaxTimeRunScoreFactory which replicates bugs necessary to match legacy results when recreating them with crispy
 * fish. Specific bugs reproduced:
 * - Penalty time is added before factoring in PAX
 * - Paxed time rounding behavior matches buggy upstream rounding. Specifically:
 *   - When the pre-rounded pax time matches ###.###98 or higher, round up
 *   - Else round down
 */
class LegacyBuggedPaxTimeRunScoreFactory(
    private val penaltyFactory: StandardPenaltyFactory
) : PaxTimeRunScoreFactory(penaltyFactory) {
    override fun score(run: Run): Score {
        val participantGrouping = run.requireParticipantSignageGrouping()
        val scratchTime = run.requireTime()
        val penalty = penaltyFactory.penalty(run)
        val value = when {
            penalty is Score.Penalty.Cone -> {
                val penalizedRawTime = penalty.floor + scratchTime.value
                (participantGrouping.paxFactor?.multiply(penalizedRawTime) ?: penalizedRawTime)
                    .setScaleWithBuggedCrispyFishRounding()
            }
            penalty != null -> {
                val paxedScratchTime = participantGrouping.paxFactor?.multiply(scratchTime.value) ?: scratchTime.value
                (penalty.floor + paxedScratchTime).setScaleWithBuggedCrispyFishRounding()
            }
            else -> (participantGrouping.paxFactor?.multiply(scratchTime.value) ?: scratchTime.value)
                .setScaleWithBuggedCrispyFishRounding()
        }
        return Score(
            value = value,
            penalty = penalty,
            strict = false
        )
    }
}