package org.coner.trailer.eventresults

import org.coner.trailer.Participant

sealed class ParticipantResult(
        val position: Int,
        val participant: Participant
) {

    init {
        require(position >= 1) { "Position is 1-indexed, argument must be greater than or equal to 1" }
    }

    class WithPersonalBestRunOnly(
            position: Int,
            participant: Participant,
            val personalBestRun: ResultRun?
    ) : ParticipantResult(position = position, participant = participant)

    class WithAllScoredRuns(
            position: Int,
            participant: Participant,
            val scoredRuns: List<ResultRun>
    ) : ParticipantResult(
            position = position,
            participant = participant
    ) {

        val personalBestRun: ResultRun? = when {
            scoredRuns.isNotEmpty() -> scoredRuns.single { it.personalBest }
            else -> null
        }
    }

    internal class Minimal(position: Int, participant: Participant) : ParticipantResult(position, participant)
}