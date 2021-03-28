package org.coner.trailer

import org.coner.trailer.eventresults.FinalScoreStyle
import org.coner.trailer.eventresults.PaxTimeStyle
import java.util.*

object TestPolicies {

    val lsccV1: Policy by lazy { Policy(
        id = UUID.randomUUID(),
        name = "LSCC Archival v1",
        conePenaltySeconds = 2,
        paxTimeStyle = PaxTimeStyle.LEGACY_BUGGED,
        finalScoreStyle = FinalScoreStyle.AUTOCROSS
    ) }

    val lsccV2: Policy by lazy { Policy(
        id = UUID.randomUUID(),
        name = "LSCC Policy v2",
        conePenaltySeconds = 2,
        paxTimeStyle = PaxTimeStyle.FAIR,
        finalScoreStyle = FinalScoreStyle.AUTOCROSS
    ) }

}