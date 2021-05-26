package org.coner.trailer.eventresults

import org.coner.trailer.Participant
import org.coner.trailer.Time

fun testParticipantResult(
    position: Int,
    score: Score,
    participant: Participant,
    scoredRunsFns: List<(Participant) -> ResultRun>,
    personalBestScoredRunIndex: Int,
    diffPrevious: Time?,
    diffFirst: Time?
) = ParticipantResult(
    position = position,
    score = score,
    participant = participant,
    scoredRuns = scoredRunsFns.map { it(participant) },
    personalBestScoredRunIndex = personalBestScoredRunIndex,
    diffPrevious = diffPrevious,
    diffFirst = diffFirst
)