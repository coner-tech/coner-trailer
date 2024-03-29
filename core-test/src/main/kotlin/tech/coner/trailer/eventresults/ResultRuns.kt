package tech.coner.trailer.eventresults

import tech.coner.trailer.Participant
import tech.coner.trailer.Run
import tech.coner.trailer.Time

fun testResultRun(
    sequence: Int,
    participant: Participant,
    time: Time,
    cones: Int = 0,
    didNotFinish: Boolean = false,
    disqualified: Boolean = false,
    rerun: Boolean = false,
    score: Score
) = ResultRun(
    run = Run(
        sequence = sequence,
        participant = participant,
        time = time,
        cones = cones,
        didNotFinish = didNotFinish,
        disqualified = disqualified,
        rerun = rerun
    ),
    score = score,
)

fun testResultRun(
    run: Run,
    score: Score?
): ResultRun? {
    return ResultRun(
        run = run,
        score = score ?: return null
    )
}