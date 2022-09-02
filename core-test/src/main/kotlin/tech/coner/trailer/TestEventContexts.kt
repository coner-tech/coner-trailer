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

    object LifecycleCases {
        private val classes = TestClasses.Lscc2019.all
        private val extendedParameters = EventExtendedParameters(
            runsPerParticipant = 2
        )
        private val participantFixtures by lazy { TestParticipants.LifecycleCases }
        private val runFixtures by lazy { TestRuns.LifecycleCases }
        object Create {
            private val base: EventContext by lazy {
                EventContext(
                    event = TestEvents.LifecycleCases.Create.noParticipantsYet,
                    classes = classes,
                    participants = emptyList(),
                    runs = emptyList(),
                    extendedParameters = extendedParameters
                )
            }
            private val eventsFixtures by lazy { TestEvents.LifecycleCases.Create }
            val noParticipantsYet: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.noParticipantsYet,
                )
            }
            val runsWithoutSignage: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.runsWithoutSignage,
                    runs = runFixtures.runsWithoutSignage
                )
            }
            val runsWithoutParticipants: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.runsWithoutParticipants,
                    runs = runFixtures.someParticipantsWithAllRuns
                )
            }
            val participantsWithoutRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.participantsWithoutRuns,
                    participants = participantFixtures.participants
                )
            }
            val someParticipantsWithSomeRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.someParticipantsWithSomeRuns,
                    participants = TestParticipants.LifecycleCases.participants,
                    runs = runFixtures.someParticipantsWithSomeRuns
                )
            }
            val someParticipantsWithAllRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.someParticipantsWithAllRuns,
                    participants = participantFixtures.participants,
                    runs = runFixtures.someParticipantsWithAllRuns
                )
            }
            val allParticipantsWithSomeRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.allParticipantsWithSomeRuns,
                    participants = participantFixtures.participants,
                    runs = runFixtures.allParticipantsWithSomeRuns
                )
            }
            val allParticipantsWithAllRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.allParticipantsWithAllRuns,
                    participants = participantFixtures.participants,
                    runs = runFixtures.allParticipantsWithAllRuns
                )
            }
        }
        object Pre {
            private val base: EventContext by lazy {
                EventContext(
                    event = TestEvents.LifecycleCases.Pre.noParticipantsYet,
                    classes = classes,
                    participants = emptyList(),
                    runs = emptyList(),
                    extendedParameters = extendedParameters
                )
            }
            private val eventsFixtures by lazy { TestEvents.LifecycleCases.Pre }
            val noParticipantsYet: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.noParticipantsYet,
                )
            }
            val runsWithoutSignage: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.runsWithoutSignage,
                    runs = runFixtures.runsWithoutSignage
                )
            }
            val runsWithoutParticipants: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.runsWithoutParticipants,
                    runs = runFixtures.someParticipantsWithAllRuns
                )
            }
            val participantsWithoutRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.participantsWithoutRuns,
                    participants = participantFixtures.participants
                )
            }
            val someParticipantsWithSomeRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.someParticipantsWithSomeRuns,
                    participants = TestParticipants.LifecycleCases.participants,
                    runs = runFixtures.someParticipantsWithSomeRuns
                )
            }
            val someParticipantsWithAllRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.someParticipantsWithAllRuns,
                    participants = participantFixtures.participants,
                    runs = runFixtures.someParticipantsWithAllRuns
                )
            }
            val allParticipantsWithSomeRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.allParticipantsWithSomeRuns,
                    participants = participantFixtures.participants,
                    runs = runFixtures.allParticipantsWithSomeRuns
                )
            }
            val allParticipantsWithAllRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.allParticipantsWithAllRuns,
                    participants = participantFixtures.participants,
                    runs = runFixtures.allParticipantsWithAllRuns
                )
            }
        }
        object Active {
            private val base: EventContext by lazy {
                EventContext(
                    event = TestEvents.LifecycleCases.Active.noParticipantsYet,
                    classes = classes,
                    participants = emptyList(),
                    runs = emptyList(),
                    extendedParameters = extendedParameters
                )
            }
            private val eventsFixtures by lazy { TestEvents.LifecycleCases.Active }
            val noParticipantsYet: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.noParticipantsYet,
                )
            }
            val runsWithoutSignage: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.runsWithoutSignage,
                    runs = runFixtures.runsWithoutSignage
                )
            }
            val runsWithoutParticipants: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.runsWithoutParticipants,
                    runs = runFixtures.someParticipantsWithAllRuns
                )
            }
            val participantsWithoutRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.participantsWithoutRuns,
                    participants = participantFixtures.participants
                )
            }
            val someParticipantsWithSomeRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.someParticipantsWithSomeRuns,
                    participants = TestParticipants.LifecycleCases.participants,
                    runs = runFixtures.someParticipantsWithSomeRuns
                )
            }
            val someParticipantsWithAllRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.someParticipantsWithAllRuns,
                    participants = participantFixtures.participants,
                    runs = runFixtures.someParticipantsWithAllRuns
                )
            }
            val allParticipantsWithSomeRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.allParticipantsWithSomeRuns,
                    participants = participantFixtures.participants,
                    runs = runFixtures.allParticipantsWithSomeRuns
                )
            }
            val allParticipantsWithAllRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.allParticipantsWithAllRuns,
                    participants = participantFixtures.participants,
                    runs = runFixtures.allParticipantsWithAllRuns
                )
            }
        }
        object Post {
            private val base: EventContext by lazy {
                EventContext(
                    event = TestEvents.LifecycleCases.Post.noParticipantsYet,
                    classes = classes,
                    participants = emptyList(),
                    runs = emptyList(),
                    extendedParameters = extendedParameters
                )
            }
            private val eventsFixtures by lazy { TestEvents.LifecycleCases.Post }
            val noParticipantsYet: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.noParticipantsYet,
                )
            }
            val runsWithoutSignage: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.runsWithoutSignage,
                    runs = runFixtures.runsWithoutSignage
                )
            }
            val runsWithoutParticipants: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.runsWithoutParticipants,
                    runs = runFixtures.someParticipantsWithAllRuns
                )
            }
            val participantsWithoutRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.participantsWithoutRuns,
                    participants = participantFixtures.participants
                )
            }
            val someParticipantsWithSomeRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.someParticipantsWithSomeRuns,
                    participants = TestParticipants.LifecycleCases.participants,
                    runs = runFixtures.someParticipantsWithSomeRuns
                )
            }
            val someParticipantsWithAllRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.someParticipantsWithAllRuns,
                    participants = participantFixtures.participants,
                    runs = runFixtures.someParticipantsWithAllRuns
                )
            }
            val allParticipantsWithSomeRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.allParticipantsWithSomeRuns,
                    participants = participantFixtures.participants,
                    runs = runFixtures.allParticipantsWithSomeRuns
                )
            }
            val allParticipantsWithAllRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.allParticipantsWithAllRuns,
                    participants = participantFixtures.participants,
                    runs = runFixtures.allParticipantsWithAllRuns
                )
            }
        }
        object Final {
            private val base: EventContext by lazy {
                EventContext(
                    event = TestEvents.LifecycleCases.Final.noParticipantsYet,
                    classes = classes,
                    participants = emptyList(),
                    runs = emptyList(),
                    extendedParameters = extendedParameters
                )
            }
            private val eventsFixtures by lazy { TestEvents.LifecycleCases.Final }
            val noParticipantsYet: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.noParticipantsYet,
                )
            }
            val runsWithoutSignage: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.runsWithoutSignage,
                    runs = runFixtures.runsWithoutSignage
                )
            }
            val runsWithoutParticipants: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.runsWithoutParticipants,
                    runs = runFixtures.someParticipantsWithAllRuns
                )
            }
            val participantsWithoutRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.participantsWithoutRuns,
                    participants = participantFixtures.participants
                )
            }
            val someParticipantsWithSomeRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.someParticipantsWithSomeRuns,
                    participants = TestParticipants.LifecycleCases.participants,
                    runs = runFixtures.someParticipantsWithSomeRuns
                )
            }
            val someParticipantsWithAllRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.someParticipantsWithAllRuns,
                    participants = participantFixtures.participants,
                    runs = runFixtures.someParticipantsWithAllRuns
                )
            }
            val allParticipantsWithSomeRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.allParticipantsWithSomeRuns,
                    participants = participantFixtures.participants,
                    runs = runFixtures.allParticipantsWithSomeRuns
                )
            }
            val allParticipantsWithAllRuns: EventContext by lazy {
                base.copy(
                    event = eventsFixtures.allParticipantsWithAllRuns,
                    participants = participantFixtures.participants,
                    runs = runFixtures.allParticipantsWithAllRuns
                )
            }
        }
    }
}