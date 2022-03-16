package org.coner.trailer.eventresults

import org.coner.trailer.Participant
import org.coner.trailer.Run
import org.coner.trailer.Time

fun testParticipantResult(
    position: Int,
    score: Score,
    participant: Participant,
    runFns: List<TestParticipantResultRunFactory>,
    personalBestScoredRunIndex: Int,
    diffPrevious: Time?,
    diffFirst: Time?
): ParticipantResult {
    val runs = runFns.map {
        it(participant)
    }
    return ParticipantResult(
        position = position,
        score = score,
        participant = participant,
        allRuns = runs.map { it.run },
        scoredRuns = runs.mapNotNull { testResultRun(it.run, it.score) },
        personalBestScoredRunIndex = personalBestScoredRunIndex,
        diffPrevious = diffPrevious,
        diffFirst = diffFirst
    )
}

typealias TestParticipantResultRunFactory = (Participant) -> Pair<Run, Score?>
val Pair<Run, Score?>.run
    get() = first
val Pair<Run, Score?>.score
    get() = second

fun testRunWithScore(
    sequence: Int,
    participant: Participant,
    time: Time,
    cones: Int = 0,
    didNotFinish: Boolean = false,
    score: Score
): Pair<Run, Score?> {
    val run = Run(
        sequence = sequence,
        participant = participant,
        time = time,
        cones = cones,
        didNotFinish = didNotFinish,
        // below are not valid when true for run with result score
        disqualified = false,
        rerun = false
    )
    return run to score
}

fun testRunWithoutScore(
    sequence: Int,
    participant: Participant,
    time: Time,
    cones: Int = 0,
    didNotFinish: Boolean = false,
    disqualified: Boolean = false,
    rerun: Boolean = false,
): Pair<Run, Score?> {
    val run = Run(
        sequence = sequence,
        participant = participant,
        time = time,
        cones = cones,
        didNotFinish = didNotFinish,
        disqualified = disqualified,
        rerun = rerun
    )
    return run to null
}
