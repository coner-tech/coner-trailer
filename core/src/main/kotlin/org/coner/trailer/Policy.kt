package org.coner.trailer

import org.coner.trailer.eventresults.FinalScoreStyle
import java.util.*

data class Policy(
    val id: UUID,
    val name: String,
    val conePenaltySeconds: Int,
    val finalScoreStyle: FinalScoreStyle
)