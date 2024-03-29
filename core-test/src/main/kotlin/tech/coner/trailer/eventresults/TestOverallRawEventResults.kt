package tech.coner.trailer.eventresults

import tech.coner.trailer.*
import java.math.BigDecimal

object TestOverallRawEventResults {
    object Lscc2019Simplified {
        val points1: OverallEventResults = OverallEventResults(
            eventContext = TestEventContexts.Lscc2019Simplified.points1,
            type = StandardEventResultsTypes.raw,
            participantResults = listOf(
                testParticipantResult(
                    position = 1,
                    participant = TestParticipants.Lscc2019Points1Simplified.EUGENE_DRAKE,
                    runFns = listOf(
                        { participant ->
                            Run(
                                sequence = 1,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("49.367")
                            ) to Score("49.367")
                        },
                        { participant ->
                            Run(
                                sequence = 2,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("49.230")
                            ) to Score("49.230")
                        },
                        { participant ->
                            Run(
                                sequence = 3,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("48.807")
                            ) to Score("48.807")
                        },
                        { participant ->
                            Run(
                                sequence = 4,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("49.573"),
                                didNotFinish = true
                            ) to Score("${Score.Penalty.DidNotFinish.floor.plus(BigDecimal("49.573"))}", Score.Penalty.DidNotFinish)
                        },
                        { participant ->
                            Run(
                                sequence = 5,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("47.544")
                            ) to Score("47.544")
                        }
                    ),
                    personalBestScoredRunIndex = 4,
                    score = Score("47.544"),
                    diffFirst = null,
                    diffPrevious = null
                ),
                testParticipantResult(
                    position = 2,
                    participant = TestParticipants.Lscc2019Points1Simplified.BRANDY_HUFF,
                    runFns = listOf(
                        { participant ->
                            Run(
                                sequence = 21,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("49.419"),
                                cones = 4
                            ) to Score("57.419", Score.Penalty.Cone("8.000", 4))
                        },
                        { participant ->
                            Run(
                                sequence = 22,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("49.848"),
                                cones = 3
                            ) to Score("55.848", Score.Penalty.Cone("6.000", 3))
                        },
                        { participant ->
                            Run(
                                sequence = 23,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("48.515")
                            ) to Score("48.515")
                        },
                        { participant ->
                            Run(
                                sequence = 24,
                                signage = participant.signage,                                participant = participant,
                                time = Time("49.076"),
                                cones = 1
                            ) to Score("51.076", Score.Penalty.Cone("2.000", 1))
                        },
                        { participant ->
                            Run(
                                sequence = 25,
                                signage = participant.signage,                                participant = participant,
                                time = Time("49.436")
                            ) to Score("49.436")
                        }
                    ),
                    personalBestScoredRunIndex = 2,
                    score = Score("48.515"),
                    diffFirst = Time("0.971"),
                    diffPrevious = Time("0.971")
                ),
                testParticipantResult(
                    position = 3,
                    participant = TestParticipants.Lscc2019Points1Simplified.JIMMY_MCKENZIE,
                    runFns = listOf(
                        { participant ->
                            Run(
                                sequence = 6,
                                signage = participant.signage,                                participant = participant,
                                time = Time("50.115"),
                                cones = 2
                            ) to Score("54.115", Score.Penalty.Cone("4.000", 2))
                        },
                        { participant ->
                            Run(
                                sequence = 7,
                                signage = participant.signage,                                participant = participant,
                                time = Time("50.162"),
                                cones = 1
                            ) to Score("52.162", Score.Penalty.Cone("2.000", 1))
                        },
                        { participant ->
                            Run(
                                sequence = 8,
                                signage = participant.signage,                                participant = participant,
                                time = Time("49.672")
                            ) to Score("49.672")
                        },
                        { participant ->
                            Run(
                                sequence = 9,
                                signage = participant.signage,                                participant = participant,
                                time = Time("49.992")
                            ) to Score("49.992")
                        },
                        { participant ->
                            Run(
                                sequence = 10,
                                signage = participant.signage,                                participant = participant,
                                time = Time("48.723")
                            ) to Score("48.723")
                        }
                    ),
                    score = Score("48.723"),
                    personalBestScoredRunIndex = 4,
                    diffFirst = Time("1.179"),
                    diffPrevious = Time("0.208")
                ),
                testParticipantResult(
                    position = 4,
                    participant = TestParticipants.Lscc2019Points1Simplified.ANASTASIA_RIGLER,
                    runFns = listOf(
                        { participant ->
                            Run(
                                sequence = 16,
                                signage = participant.signage,                                participant = participant,
                                time = Time("53.693")
                            ) to Score("53.693")
                        },
                        { participant ->
                            Run(
                                sequence = 17,
                                signage = participant.signage,                                participant = participant,
                                time = Time("52.179"),
                                cones = 1
                            ) to Score("54.179", Score.Penalty.Cone("2.000", 1))
                        },
                        { participant ->
                            Run(
                                sequence = 18,
                                signage = participant.signage,                                participant = participant,
                                time = Time("52.256")
                            ) to Score("52.256")
                        },
                        { participant ->
                            Run(
                                sequence = 19,
                                signage = participant.signage,                                participant = participant,
                                time = Time("51.323")
                            ) to Score("51.323")
                        },
                        { participant ->
                            Run(
                                sequence = 20,
                                signage = participant.signage,                                participant = participant,
                                time = Time("51.344")
                            ) to Score("51.344")
                        }
                    ),
                    score = Score("51.323"),
                    personalBestScoredRunIndex = 3,
                    diffFirst = Time("3.779"),
                    diffPrevious = Time("2.600")
                ),
                testParticipantResult(
                    position = 5,
                    participant = TestParticipants.Lscc2019Points1Simplified.REBECCA_JACKSON,
                    runFns = listOf(
                        { participant ->
                            Run(
                                sequence = 11,
                                signage = participant.signage,                                participant = participant,
                                time = Time("52.749")
                            ) to Score("52.749")
                        },
                        { participant ->
                            Run(
                                sequence = 12,
                                signage = participant.signage,                                participant = participant,
                                time = Time("53.175")
                            ) to Score("53.175")
                        },
                        { participant ->
                            Run(
                                sequence = 13,
                                signage = participant.signage,                                participant = participant,
                                time = Time("52.130")
                            ) to Score("52.130")
                        },
                        { participant ->
                            Run(
                                sequence = 14,
                                signage = participant.signage,                                participant = participant,
                                time = Time("52.117")
                            ) to Score("52.117")
                        },
                        { participant ->
                            Run(
                                sequence = 15,
                                signage = participant.signage,                                participant = participant,
                                time = Time("51.408")
                            ) to Score("51.408")
                        }
                    ),
                    score = Score("51.408"),
                    personalBestScoredRunIndex = 4,
                    diffFirst = Time("3.864"),
                    diffPrevious = Time("0.085")
                ),
                testParticipantResult(
                    position = 6,
                    participant = TestParticipants.Lscc2019Points1Simplified.BRYANT_MORAN,
                    runFns = listOf(
                        { participant ->
                            Run(
                                sequence = 26,
                                signage = participant.signage,                                participant = participant,
                                time = Time("56.353"),
                                cones = 1
                            ) to Score("58.353", Score.Penalty.Cone("2.000", 1))
                        },
                        { participant ->
                            Run(
                                sequence = 27,
                                signage = participant.signage,                                participant = participant,
                                time = Time("55.831")
                            ) to Score("55.831")
                        },
                        { participant ->
                            Run(
                                sequence = 28,
                                signage = participant.signage,                                participant = participant,
                                time = Time("52.201")
                            ) to Score("52.201")
                        },
                        { participant ->
                            Run(
                                sequence = 29,
                                signage = participant.signage,                                participant = participant,
                                time = Time("52.062"),
                                cones = 1
                            ) to Score("54.062", Score.Penalty.Cone("2.000", 1))
                        },
                        { participant ->
                            Run(
                                sequence = 30,
                                signage = participant.signage,                                participant = participant,
                                time = Time("53.074")
                            ) to Score("53.074")
                        }
                    ),
                    score = Score("52.201"),
                    personalBestScoredRunIndex = 2,
                    diffFirst = Time("4.657"),
                    diffPrevious = Time("0.793")
                ),
                testParticipantResult(
                    position = 7,
                    participant = TestParticipants.Lscc2019Points1Simplified.DOMINIC_ROGERS,
                    runFns = listOf(
                        { participant ->
                            Run(
                                sequence = 31,
                                signage = participant.signage,                                participant = participant,
                                time = Time("54.246")
                            ) to Score("54.246")
                        },
                        { participant ->
                            Run(
                                sequence = 32,
                                signage = participant.signage,                                participant = participant,
                                time = Time("53.629"),
                                cones = 1
                            ) to Score("55.629", Score.Penalty.Cone("2.000", 1))
                        },
                        { participant ->
                            Run(
                                sequence = 33,
                                signage = participant.signage,                                participant = participant,
                                time = Time("51.856"),
                                cones = 1
                            ) to Score("53.856", Score.Penalty.Cone("2.000", 1))
                        },
                        { participant ->
                            Run(
                                sequence = 34,
                                signage = participant.signage,                                participant = participant,
                                time = Time("53.409")
                            ) to Score("53.409")
                        },
                        { participant ->
                            Run(
                                sequence = 35,
                                signage = participant.signage,                                participant = participant,
                                time = Time("52.447")
                            ) to Score("52.447")
                        }
                    ),
                    score = Score("52.447"),
                    personalBestScoredRunIndex = 4,
                    diffFirst = Time("4.903"),
                    diffPrevious = Time("0.246")
                )
            )
        )
    }

    object Lifecycles {
        private val classes = TestClasses.Lscc2019
        private val participants = TestParticipants.Lifecycles
        private val runs = TestRuns.Lifecycles
        object Create {
            private val eventContexts = TestEventContexts.Lifecycles.Create
            val noParticipantsYet by lazy {
                OverallEventResults(
                    eventContext = eventContexts.noParticipantsYet,
                    type = StandardEventResultsTypes.raw,
                    participantResults = emptyList()
                )
            }
            val runsWithoutSignage by lazy {
                OverallEventResults(
                    eventContext = eventContexts.runsWithoutSignage,
                    type = StandardEventResultsTypes.raw,
                    participantResults = emptyList()
                )
            }
            val runsWithoutParticipants by lazy {
                OverallEventResults(
                    eventContext = eventContexts.runsWithoutParticipants,
                    type = StandardEventResultsTypes.raw,
                    participantResults = emptyList()
                )
            }
            val participantsWithoutRuns by lazy {
                OverallEventResults(
                    eventContext = eventContexts.participantsWithoutRuns,
                    type = StandardEventResultsTypes.raw,
                    participantResults = emptyList()
                )
            }
            val someParticipantsWithSomeRuns by lazy {
                val runs = runs.someParticipantsWithSomeRuns
                OverallEventResults(
                    eventContext = eventContexts.someParticipantsWithSomeRuns,
                    type = StandardEventResultsTypes.raw,
                    participantResults = listOf(
                        testParticipantResult(
                            position = 1,
                            score = Score("34.567"),
                            participant = participants.REBECCA_JACKSON,
                            diffFirst = null,
                            diffPrevious = null,
                            personalBestScoredRunIndex = 0,
                            runFns = listOf { runs[0] to Score("34.567") }
                        )
                    )
                )
            }
            val someParticipantsWithAllRuns: OverallEventResults by lazy {
                val runs = runs.someParticipantsWithAllRuns
                OverallEventResults(
                    eventContext = eventContexts.someParticipantsWithAllRuns,
                    type = StandardEventResultsTypes.raw,
                    participantResults = listOf(
                        testParticipantResult(
                            position = 1,
                            score = Score("34.456"),
                            participant = participants.REBECCA_JACKSON,
                            diffFirst = null,
                            diffPrevious = null,
                            personalBestScoredRunIndex = 1,
                            runFns = listOf(
                                { runs[0] to Score("34.567") },
                                { runs[2] to Score("34.456") }
                            )
                        ),
                        testParticipantResult(
                            position = 2,
                            score = Score("35.678"),
                            participant = participants.JIMMY_MCKENZIE,
                            diffFirst = Time("1.222"),
                            diffPrevious = Time("1.222"),
                            personalBestScoredRunIndex = 0,
                            runFns = listOf { runs[1] to Score("35.678") }
                        )
                    )
                )
            }
            val allParticipantsWithSomeRuns: OverallEventResults by lazy {
                val runs = runs.allParticipantsWithSomeRuns
                OverallEventResults(
                    eventContext = eventContexts.allParticipantsWithSomeRuns,
                    type = StandardEventResultsTypes.raw,
                    participantResults = listOf(
                        testParticipantResult(
                            position = 1,
                            score = Score("34.567"),
                            participant = participants.REBECCA_JACKSON,
                            diffFirst = null,
                            diffPrevious = null,
                            personalBestScoredRunIndex = 0,
                            runFns = listOf { runs[0] to Score("34.567") }
                        ),
                        testParticipantResult(
                            position = 2,
                            score = Score("35.678"),
                            participant = participants.JIMMY_MCKENZIE,
                            diffFirst = Time("1.111"),
                            diffPrevious = Time("1.111"),
                            personalBestScoredRunIndex = 0,
                            runFns = listOf { runs[1] to Score("35.678") }
                        )
                    )
                )
            }
            val allParticipantsWithAllRuns: OverallEventResults by lazy {
                val runs = runs.allParticipantsWithAllRuns
                OverallEventResults(
                    eventContext = eventContexts.allParticipantsWithAllRuns,
                    type = StandardEventResultsTypes.raw,
                    participantResults = listOf(
                        testParticipantResult(
                            position = 1,
                            score = Score("34.456"),
                            participant = participants.REBECCA_JACKSON,
                            diffFirst = null,
                            diffPrevious = null,
                            personalBestScoredRunIndex = 1,
                            runFns = listOf(
                                { runs[0] to Score("34.567") },
                                { runs[2] to Score("34.456") }
                            )
                        ),
                        testParticipantResult(
                            position = 2,
                            score = Score("35.567"),
                            participant = participants.JIMMY_MCKENZIE,
                            diffFirst = Time("1.111"),
                            diffPrevious = Time("1.111"),
                            personalBestScoredRunIndex = 1,
                            runFns = listOf(
                                { runs[1] to Score("35.678") },
                                { runs[3] to Score("35.567") }
                            )
                        )
                    )
                )
            }
        }
        object Pre {
            private val eventContexts = TestEventContexts.Lifecycles.Pre
            val noParticipantsYet by lazy {
                Create.noParticipantsYet
                    .copy(eventContext = eventContexts.noParticipantsYet)
            }
            val runsWithoutSignage by lazy {
                Create.runsWithoutSignage
                    .copy(eventContext = eventContexts.runsWithoutSignage)
            }
            val runsWithoutParticipants by lazy {
                Create.runsWithoutParticipants
                    .copy(eventContext = eventContexts.runsWithoutParticipants)
            }
            val participantsWithoutRuns by lazy {
                Create.participantsWithoutRuns
                    .copy(eventContext = eventContexts.participantsWithoutRuns)
            }
            val someParticipantsWithSomeRuns by lazy {
                Create.someParticipantsWithSomeRuns
                    .copy(eventContext = eventContexts.someParticipantsWithSomeRuns)
            }
            val someParticipantsWithAllRuns: OverallEventResults by lazy {
                Create.someParticipantsWithAllRuns
                    .copy(eventContext = eventContexts.someParticipantsWithAllRuns)
            }
            val allParticipantsWithSomeRuns: OverallEventResults by lazy {
                Create.allParticipantsWithSomeRuns
                    .copy(eventContext = eventContexts.allParticipantsWithSomeRuns)
            }
            val allParticipantsWithAllRuns: OverallEventResults by lazy {
                Create.allParticipantsWithAllRuns
                    .copy(eventContext = eventContexts.allParticipantsWithAllRuns)
            }
        }
        object Active {
            private val eventContexts = TestEventContexts.Lifecycles.Active
            val noParticipantsYet by lazy {
                Create.noParticipantsYet
                    .copy(eventContext = eventContexts.noParticipantsYet)
            }
            val runsWithoutSignage by lazy {
                Create.runsWithoutSignage
                    .copy(eventContext = eventContexts.runsWithoutSignage)
            }
            val runsWithoutParticipants by lazy {
                Create.runsWithoutParticipants
                    .copy(eventContext = eventContexts.runsWithoutParticipants)
            }
            val participantsWithoutRuns by lazy {
                Create.participantsWithoutRuns
                    .copy(eventContext = eventContexts.participantsWithoutRuns)
            }
            val someParticipantsWithSomeRuns by lazy {
                Create.someParticipantsWithSomeRuns
                    .copy(eventContext = eventContexts.someParticipantsWithSomeRuns)
            }
            val someParticipantsWithAllRuns: OverallEventResults by lazy {
                Create.someParticipantsWithAllRuns
                    .copy(eventContext = eventContexts.someParticipantsWithAllRuns)
            }
            val allParticipantsWithSomeRuns: OverallEventResults by lazy {
                Create.allParticipantsWithSomeRuns
                    .copy(eventContext = eventContexts.allParticipantsWithSomeRuns)
            }
            val allParticipantsWithAllRuns: OverallEventResults by lazy {
                Create.allParticipantsWithAllRuns
                    .copy(eventContext = eventContexts.allParticipantsWithAllRuns)
            }
        }
        object Post {
            private val eventContexts = TestEventContexts.Lifecycles.Post
            val noParticipantsYet by lazy {
                Create.noParticipantsYet
                    .copy(eventContext = eventContexts.noParticipantsYet)
            }
            val runsWithoutSignage by lazy {
                Create.runsWithoutSignage
                    .copy(eventContext = eventContexts.runsWithoutSignage)
            }
            val runsWithoutParticipants by lazy {
                Create.runsWithoutParticipants
                    .copy(eventContext = eventContexts.runsWithoutParticipants)
            }
            val participantsWithoutRuns by lazy {
                Create.participantsWithoutRuns
                    .copy(eventContext = eventContexts.participantsWithoutRuns)
            }
            val someParticipantsWithSomeRuns by lazy {
                Create.someParticipantsWithSomeRuns
                    .copy(eventContext = eventContexts.someParticipantsWithSomeRuns)
            }
            val someParticipantsWithAllRuns: OverallEventResults by lazy {
                Create.someParticipantsWithAllRuns
                    .copy(eventContext = eventContexts.someParticipantsWithAllRuns)
            }
            val allParticipantsWithSomeRuns: OverallEventResults by lazy {
                Create.allParticipantsWithSomeRuns
                    .copy(eventContext = eventContexts.allParticipantsWithSomeRuns)
            }
            val allParticipantsWithAllRuns: OverallEventResults by lazy {
                Create.allParticipantsWithAllRuns
                    .copy(eventContext = eventContexts.allParticipantsWithAllRuns)
            }
        }
        object Final {
            private val eventContexts = TestEventContexts.Lifecycles.Final
            val noParticipantsYet by lazy {
                Create.noParticipantsYet
                    .copy(eventContext = eventContexts.noParticipantsYet)
            }
            val runsWithoutSignage by lazy {
                Create.runsWithoutSignage
                    .copy(eventContext = eventContexts.runsWithoutSignage)
            }
            val runsWithoutParticipants by lazy {
                Create.runsWithoutParticipants
                    .copy(eventContext = eventContexts.runsWithoutParticipants)
            }
            val participantsWithoutRuns by lazy {
                Create.participantsWithoutRuns
                    .copy(eventContext = eventContexts.participantsWithoutRuns)
            }
            val someParticipantsWithSomeRuns by lazy {
                Create.someParticipantsWithSomeRuns
                    .copy(eventContext = eventContexts.someParticipantsWithSomeRuns)
            }
            val someParticipantsWithAllRuns: OverallEventResults by lazy {
                Create.someParticipantsWithAllRuns
                    .copy(eventContext = eventContexts.someParticipantsWithAllRuns)
            }
            val allParticipantsWithSomeRuns: OverallEventResults by lazy {
                Create.allParticipantsWithSomeRuns
                    .copy(eventContext = eventContexts.allParticipantsWithSomeRuns)
            }
            val allParticipantsWithAllRuns: OverallEventResults by lazy {
                Create.allParticipantsWithAllRuns
                    .copy(eventContext = eventContexts.allParticipantsWithAllRuns)
            }
        }
    }
}