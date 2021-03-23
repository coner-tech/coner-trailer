package org.coner.trailer.datasource.crispyfish.eventsresults

import org.coner.trailer.Grouping
import org.coner.trailer.Time
import org.coner.trailer.eventresults.PaxTimeRunScoreFactory
import org.coner.trailer.eventresults.Score
import org.coner.trailer.eventresults.StandardPenaltyFactory
import java.math.BigDecimal

/**
 * A PaxTimeRunScoreFactory which replicates bugs necessary to match legacy results when recreating them with crispy
 * fish. Specific bugs reproduced:
 * - Penalty time is added before factoring in PAX
 * - Paxed time rounding behavior matches buggy upstream rounding. Specifically:
 *   - When the pre-rounded pax time matches ###.###9 or higher, round up
 *   - Else round down
 */
class LegacyBuggedPaxTimeRunScoreFactory(
    private val penaltyFactory: StandardPenaltyFactory
) : PaxTimeRunScoreFactory(penaltyFactory) {
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
        val penalizedTime = when {
            penalty != null -> penalty.floor + scratchTime.value
            else -> scratchTime.value
        }
        val buggedPaxTime = (penalizedTime * (participantGrouping.paxFactor ?: BigDecimal.ONE))
        TODO("when buggedPaxTime is ##.###9 or higher, round up, else round down")
        return Score(
            value = buggedPaxTime,
            penalty = penalty,
            strict = false
        )
    }
}