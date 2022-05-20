package tech.coner.trailer

import tech.coner.trailer.eventresults.FinalScoreStyle
import tech.coner.trailer.eventresults.PaxTimeStyle
import java.util.*

data class Policy(
    val id: UUID,
    val club: Club,
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