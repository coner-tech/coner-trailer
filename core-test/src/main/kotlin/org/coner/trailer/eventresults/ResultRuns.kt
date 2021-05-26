package org.coner.trailer.eventresults

import org.coner.trailer.Participant
import org.coner.trailer.Run
import org.coner.trailer.Time

fun testResultRun(
    sequence: Int,
    participant: Participant,
    time: Time,
    cones: Int? = null,
    didNotFinish: Boolean? = null,
    disqualified: Boolean? = null,
    rerun: Boolean? = null,
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