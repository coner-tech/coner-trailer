package org.coner.trailer.eventresults

import org.coner.trailer.Participant
import org.coner.trailer.Run
import org.coner.trailer.Time

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