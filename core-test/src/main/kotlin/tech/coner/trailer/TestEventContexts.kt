package tech.coner.trailer

object TestEventContexts {

    object Lscc2019 {
        val points1: EventContext by lazy {
            EventContext(
                event = TestEvents.Lscc2019.points1,
                classes = TestClasses.Lscc2019.all,
                participants = TestParticipants.Lscc2019Points1Simplified.all,
                runs = TODO(),
                extendedParameters = TODO()
            )
        }
    }

    object Lscc2019Simplified {
        val points1: EventContext by lazy {
            EventContext(
                event = TestEvents.Lscc2019Simplified.points1,
                classes = TestClasses.Lscc2019.all,
                participants = TestParticipants.Lscc2019Points1Simplified.all,
                runs = TestRuns.lscc2019Points1Simplified,
                extendedParameters = EventExtendedParameters(runsPerParticipant = 5)
            )
        }
        val points2: EventContext by lazy {
            EventContext(
                event = TestEvents.Lscc2019Simplified.points2,
                classes = TestClasses.Lscc2019.all,
                participants = TestParticipants.Lscc2019Points2Simplified.all,
                runs = TODO(),
                extendedParameters = TODO()
            )
        }
    }

    object LsccTieBreaking {
        val points1: EventContext by lazy {
            EventContext(
                event = TestEvents.Lscc2019.points1,
                classes = TestClasses.Lscc2019.all,
                participants = TestParticipants.LsccTieBreaking.all,
                runs = TestRuns.lsccTieBreakingPoints1,
                extendedParameters = EventExtendedParameters(
                    runsPerParticipant = 1
                )
            )
        }
        val points2: EventContext by lazy {
            points1.copy(
                event = TestEvents.Lscc2019.points2,
                runs = TestRuns.lsccTieBreakingPoints2
            )
        }
    }




}