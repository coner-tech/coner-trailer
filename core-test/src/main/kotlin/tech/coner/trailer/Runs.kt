package tech.coner.trailer

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
    signage = participant.signage,
    participant = participant,
    time = time,
    cones = cones,
    didNotFinish = didNotFinish,
    disqualified = disqualified,
    rerun = rerun
)

fun testRun(
    sequence: Int,
    participant: Participant,
    timeAsString: String,
    cones: Int = 0,
    didNotFinish: Boolean = false,
    disqualified: Boolean = false,
    rerun: Boolean = false,
) = Run(
    sequence = sequence,
    signage = participant.signage,
    participant = participant,
    time = Time(timeAsString),
    cones = cones,
    didNotFinish = didNotFinish,
    disqualified = disqualified,
    rerun = rerun
)