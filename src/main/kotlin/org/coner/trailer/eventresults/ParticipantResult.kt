package org.coner.trailer.eventresults

import org.coner.trailer.Participant
import org.coner.trailer.Time

sealed class ParticipantResult(
        val position: Int,
        val participant: Participant,
        val marginOfVictory: Time?,
        val marginOfLoss: Time?
) {

    init {
        require(position >= 1) { "Position is 1-indexed, argument must be greater than or equal to 1" }
    }

    class WithPersonalBestRunOnly(
            position: Int,
            participant: Participant,
            marginOfVictory: Time?,
            marginOfLoss: Time?,
            val personalBestRun: ResultRun?
    ) : ParticipantResult(
            position = position,
            participant = participant,
            marginOfVictory = marginOfVictory,
            marginOfLoss = marginOfLoss
    )

    class WithAllScoredRuns(
            position: Int,
            participant: Participant,
            marginOfVictory: Time?,
            marginOfLoss: Time?,
            val scoredRuns: List<ResultRun>
    ) : ParticipantResult(
            position = position,
            participant = participant,
            marginOfVictory = marginOfVictory,
            marginOfLoss = marginOfLoss
    ) {

        val personalBestRun: ResultRun? = when {
            scoredRuns.isNotEmpty() -> scoredRuns.single { it.personalBest }
            else -> null
        }
    }

    internal class Minimal(
            position: Int,
            participant: Participant,
            marginOfVictory: Time? = null,
            marginOfLoss: Time? = null
    ) : ParticipantResult(
            position = position,
            participant = participant,
            marginOfVictory = marginOfVictory,
            marginOfLoss = marginOfLoss
    )
}