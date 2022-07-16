package tech.coner.trailer

object TestEventContexts {

    object Lscc2019 {
        val points1: EventContext by lazy {
            EventContext(
                event = TestEvents.Lscc2019.points1,
                classes = TestClasses.Lscc2019.all,
                participants = TestParticipants.Lscc2019Points1Simplified.all,
                runs = emptyList(), // not relevant to test
                extendedParameters = EventExtendedParameters(
                    runsPerParticipant = 5
                )
            )
        }
    }

    object Lscc2019Simplified {
        val points1: EventContext by lazy {
            EventContext(
                event = TestEvents.Lscc2019Simplified.points1,
                classes = TestClasses.Lscc2019.all,
                participants = TestParticipants.Lscc2019Points1Simplified.all,
                runs = TestRuns.Lscc2019Simplified.points1,
                extendedParameters = EventExtendedParameters(runsPerParticipant = 5)
            )
        }
        val points2: EventContext by lazy {
            EventContext(
                event = TestEvents.Lscc2019Simplified.points2,
                classes = TestClasses.Lscc2019.all,
                participants = TestParticipants.Lscc2019Points2Simplified.all,
                runs = TestRuns.Lscc2019Simplified.points2,
                extendedParameters = EventExtendedParameters(
                    runsPerParticipant = 4
                )
            )
        }
        val points3: EventContext by lazy {
            EventContext(
                event = TestEvents.Lscc2019Simplified.points3,
                classes = TestClasses.Lscc2019.all,
                participants = TestParticipants.Lscc2019Points3Simplified.all,
                runs = TestRuns.Lscc2019Simplified.points3,
                extendedParameters = EventExtendedParameters(
                    runsPerParticipant = 4
                )
            )
        }
    }

    object LsccTieBreaking {
        val points1: EventContext by lazy {
            EventContext(
                event = TestEvents.Lscc2019.points1,
                classes = TestClasses.Lscc2019.all,
                participants = TestParticipants.LsccTieBreaking.all,
                runs = TestRuns.LsccTieBreaking.points1,
                extendedParameters = EventExtendedParameters(
                    runsPerParticipant = 1
                )
            )
        }
        val points2: EventContext by lazy {
            points1.copy(
                event = TestEvents.Lscc2019.points2,
                runs = TestRuns.LsccTieBreaking.points2
            )
        }
    }




}