package tech.coner.trailer.eventresults

import tech.coner.trailer.Run
import java.math.BigDecimal
import java.math.RoundingMode

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
        val participantClassing = run.requireParticipantClassing()
        val scratchTime = run.requireTime()
        val penalty = penaltyFactory.penalty(run)
        val value = when {
            penalty is Score.Penalty.Cone -> {
                val penalizedRawTime = penalty.floor + scratchTime.value
                (participantClassing.paxFactor.multiply(penalizedRawTime) ?: penalizedRawTime)
                    .setScaleWithBuggedCrispyFishRounding()
            }
            penalty != null -> {
                val paxedScratchTime = participantClassing.paxFactor.multiply(scratchTime.value) ?: scratchTime.value
                (penalty.floor + paxedScratchTime).setScaleWithBuggedCrispyFishRounding()
            }
            else -> (participantClassing.paxFactor.multiply(scratchTime.value) ?: scratchTime.value)
                .setScaleWithBuggedCrispyFishRounding()
        }
        return Score(
            value = value,
            penalty = penalty,
            strict = false
        )
    }

    fun BigDecimal.setScaleWithBuggedCrispyFishRounding(): BigDecimal {
        val roundingMode = if (scale() > 3) {
            val decimalDigits = toString().substringAfter('.')
            if (decimalDigits[3] == '9') {
                val tenThousandth = decimalDigits[4]
                if (tenThousandth == '9' || tenThousandth == '8' || tenThousandth == '7' || tenThousandth == '6' ) {
                    RoundingMode.CEILING
                } else {
                    RoundingMode.FLOOR
                }
            } else {
                RoundingMode.FLOOR
            }
        } else {
            RoundingMode.FLOOR
        }
        return setScale(3, roundingMode)
    }
}