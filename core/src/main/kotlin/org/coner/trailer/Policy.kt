package org.coner.trailer

import org.coner.trailer.eventresults.FinalScoreStyle
import org.coner.trailer.eventresults.PaxTimeStyle
import org.coner.trailer.eventresults.Score
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
    /**
     * Declare the score to pad out runs for drivers who haven't completed all their runs (yet?), for use with tie-breaking. Recommend to set it high, such as 999.999.
     */
    val participantResultScoredRunsPad: Score
) {

    sealed class RunSource {
        /**
         * Source runs from crispyfish
         */
        object CrispyFish : RunSource()
    }
}