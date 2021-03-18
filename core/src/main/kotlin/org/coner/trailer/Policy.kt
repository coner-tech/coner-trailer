package org.coner.trailer

import org.coner.trailer.eventresults.FinalScorePolicy

data class Policy(
    val conePenaltySeconds: Int,
    val finalScore: FinalScorePolicy
)