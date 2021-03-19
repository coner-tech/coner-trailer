package org.coner.trailer

import org.coner.trailer.eventresults.FinalScoreStyle

data class Policy(
    val conePenaltySeconds: Int,
    val finalScoreStyle: FinalScoreStyle
)