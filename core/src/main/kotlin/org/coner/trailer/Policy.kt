package org.coner.trailer

import org.coner.trailer.eventresults.FinalScoreStyle
import org.coner.trailer.eventresults.PaxTimeStyle
import java.util.*

data class Policy(
    val id: UUID,
    val name: String,
    val conePenaltySeconds: Int,
    val paxTimeStyle: PaxTimeStyle,
    val finalScoreStyle: FinalScoreStyle,
    /**
     * Declare the authoritative source of run information
     */
    val authoritativeRunSource: RunSource,
) {

    sealed class RunSource {
        /**
         * Source runs from crispyfish
         */
        object CrispyFish : RunSource()
    }
}