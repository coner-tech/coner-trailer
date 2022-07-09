package tech.coner.trailer.eventresults

import tech.coner.trailer.Policy
import tech.coner.trailer.Run
import java.math.BigDecimal

class StandardPenaltyFactory(
    private val policy: Policy
) {
    fun penalty(run: Run): Score.Penalty? {
        return when {
            run.disqualified -> Score.Penalty.Disqualified
            run.didNotFinish -> Score.Penalty.DidNotFinish
            run.cones > 0 -> Score.Penalty.Cone(
                floor = BigDecimal(policy.conePenaltySeconds) * BigDecimal(run.cones).setScale(3),
                count = run.cones
            )
            else -> null
        }
    }
}