package org.coner.trailer.eventresults

import org.coner.trailer.Policy
import java.math.BigDecimal

class StandardPenaltyFactory(
    private val policy: Policy
) {
    fun penalty(
        cones: Int?,
        didNotFinish: Boolean?,
        disqualified: Boolean?
    ): Score.Penalty? {
        return when {
            disqualified == true -> Score.Penalty.Disqualified
            didNotFinish == true -> Score.Penalty.DidNotFinish
            cones != null && cones > 0 -> Score.Penalty.Cone(
                floor = BigDecimal(policy.conePenaltySeconds) * BigDecimal(cones).setScale(3),
                count = cones
            )
            else -> null
        }
    }
}