package tech.coner.trailer

object TestEventContexts {
    val Lscc2019Points1Simplified: EventContext by lazy {
        EventContext(
            event = TestEvents.Lscc2019Simplified.points1,
            classes = TestClasses.Lscc2019.all,
            participants = TestParticipants.Lscc2019Points1Simplified.all,
            runs = TestRuns.lscc2019Points1Simplified,
            extendedParameters = EventExtendedParameters(runsPerParticipant = 5)
        )
    }
    val LsccTieBreakingPoints1: EventContext by lazy {
        EventContext(
            event = TestEvents.Lscc2019.points1,
            classes = TestClasses.Lscc2019.all,
            participants = TestParticipants.LsccTieBreaking.all,
            runs = TestRuns.lsccTieBreakingPoints1,
            extendedParameters = EventExtendedParameters(
                runsPerParticipant = TODO()
            )
        )
    }
}