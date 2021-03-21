package org.coner.trailer

import org.coner.trailer.eventresults.FinalScoreStyle
import java.util.*

object TestPolicies {

    val lsccV1: Policy by lazy { Policy(
        id = UUID.randomUUID(),
        name = "LSCC Policy v1",
        conePenaltySeconds = 2,
        finalScoreStyle = FinalScoreStyle.AUTOCROSS
    ) }

}