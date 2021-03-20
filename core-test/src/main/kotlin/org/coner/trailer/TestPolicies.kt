package org.coner.trailer

import org.coner.trailer.eventresults.FinalScoreStyle

object TestPolicies {

    val lsccV1: Policy by lazy { Policy(
        conePenaltySeconds = 2,
        finalScoreStyle = FinalScoreStyle.AUTOCROSS
    ) }

}