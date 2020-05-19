package org.coner.trailer.eventresults

import org.coner.trailer.Participant

sealed class ParticipantResult(
        val position: Int,
        val participant: Participant
) {
    class WithBestScoredRunOnly(
            position: Int,
            participant: Participant,
            val bestRun: ResultRun
    ) : ParticipantResult(position = position, participant = participant)

    class WithScoredRuns(
            position: Int,
            participant: Participant,
            val scoredRuns: List<ResultRun>,
            val bestRun: ResultRun
    ) : ParticipantResult(
            position = position,
            participant = participant
    )
}