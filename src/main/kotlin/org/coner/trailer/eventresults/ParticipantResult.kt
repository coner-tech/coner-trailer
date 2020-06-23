package org.coner.trailer.eventresults

import org.coner.trailer.Participant
import org.coner.trailer.Time

data class ParticipantResult(
        val position: Int,
        val participant: Participant,
        val marginOfVictory: Time?,
        val marginOfLoss: Time?,
        val scoredRuns: List<ResultRun>
) {

    init {
        require(position >= 1) { "Position is 1-indexed, argument must be greater than or equal to 1" }
    }

    val personalBestRun: ResultRun? by lazy {
        when {
            scoredRuns.isNotEmpty() -> scoredRuns.single { it.personalBest }
            else -> null
        }
    }
}