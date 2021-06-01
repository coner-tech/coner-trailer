package org.coner.trailer

fun testRun(
    sequence: Int,
    participant: Participant,
    time: Time,
    cones: Int = 0,
    didNotFinish: Boolean = false,
    disqualified: Boolean = false,
    rerun: Boolean = false,
) = Run(
    sequence = sequence,
    participant = participant,
    time = time,
    cones = cones,
    didNotFinish = didNotFinish,
    disqualified = disqualified,
    rerun = rerun
)