package tech.coner.trailer.eventresults

import tech.coner.trailer.*

object TestOverallPaxEventResults {
    object Lscc2019Simplified {
        val points1: OverallEventResults = OverallEventResults(
            eventContext = TestEventContexts.Lscc2019Simplified.points1,
            type = StandardEventResultsTypes.pax,
            participantResults = listOf(
                testParticipantResult(
                    position = 1,
                    participant = TestParticipants.Lscc2019Points1Simplified.BRANDY_HUFF,
                    runFns = listOf(
                        { participant ->
                            Run(
                                sequence = 21,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("49.419"),
                                cones = 4
                            ) to Score("46.509", Score.Penalty.Cone("8.000", 4), strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 22,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("49.848"),
                                cones = 3
                            ) to Score("45.236", Score.Penalty.Cone("6.000", 3), strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 23,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("48.515")
                            ) to Score("39.297", strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 24,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("49.076"),
                                cones = 1
                            ) to Score("41.371", Score.Penalty.Cone("2.000", 1), strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 25,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("49.436")
                            ) to Score("40.043", strict = false)
                        }
                    ),
                    score = Score("39.297", strict = false),
                    personalBestScoredRunIndex = 2,
                    diffFirst = null,
                    diffPrevious = null
                ),
                testParticipantResult(
                    position = 2,
                    participant = TestParticipants.Lscc2019Points1Simplified.EUGENE_DRAKE,
                    runFns = listOf(
                        { participant ->
                            Run(
                                sequence = 1,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("49.367")
                            ) to Score("40.826", strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 2,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("49.230")
                            ) to Score("40.713", strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 3,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("48.807")
                            ) to Score("40.363", strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 4,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("49.573"),
                                didNotFinish = true
                            ) to Score("214748404.996", Score.Penalty.DidNotFinish, strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 5,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("47.544")
                            ) to Score("39.318", strict = false)
                        }
                    ),
                    score = Score("39.318", strict = false),
                    personalBestScoredRunIndex = 4,
                    diffFirst = Time("0.021"),
                    diffPrevious = Time("0.021")
                ),
                testParticipantResult(
                    position = 3,
                    participant = TestParticipants.Lscc2019Points1Simplified.ANASTASIA_RIGLER,
                    runFns = listOf(
                        { participant ->
                            Run(
                                sequence = 16,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("53.693")
                            ) to Score("41.880", strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 17,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("52.179"),
                                cones = 1
                            ) to Score("42.259", Score.Penalty.Cone("2.000", 1), strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 18,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("52.256")
                            ) to Score("40.759", strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 19,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("51.323")
                            ) to Score("40.031", strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 20,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("51.344")
                            ) to Score("40.048", strict = false)
                        }
                    ),
                    score = Score("40.031", strict = false),
                    personalBestScoredRunIndex = 3,
                    diffFirst = Time("0.734"),
                    diffPrevious = Time("0.713")
                ),
                testParticipantResult(
                    position = 4,
                    participant = TestParticipants.Lscc2019Points1Simplified.REBECCA_JACKSON,
                    runFns = listOf(
                        { participant ->
                            Run(
                                sequence = 11,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("52.749")
                            ) to Score("41.144", strict = false)
                        },
                        { participant ->

                            Run(
                                sequence = 12,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("53.175")
                            ) to Score("41.476", strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 13,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("52.130")
                            ) to Score("40.661", strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 14,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("52.117")
                            ) to Score("40.651", strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 15,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("51.408")
                            ) to Score("40.098", strict = false)
                        }
                    ),
                    score = Score("40.098", strict = false),
                    personalBestScoredRunIndex = 4,
                    diffFirst = Time("0.801"),
                    diffPrevious = Time("0.067")
                ),
                testParticipantResult(
                    position = 5,
                    participant = TestParticipants.Lscc2019Points1Simplified.JIMMY_MCKENZIE,
                    runFns = listOf(
                        { participant ->
                            Run(
                                sequence = 6,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("50.115"),
                                cones = 2
                            ) to Score("44.753", Score.Penalty.Cone("4.000", 2), strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 7,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("50.162"),
                                cones = 1
                            ) to Score("43.138", Score.Penalty.Cone("2.000", 1), strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 8,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("49.672")
                            ) to Score("41.078", strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 9,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("49.992")
                            ) to Score("41.343", strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 10,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("48.723")
                            ) to Score("40.293", strict = false)
                        }
                    ),
                    score = Score("40.293", strict = false),
                    personalBestScoredRunIndex = 4,
                    diffFirst = Time("0.996"),
                    diffPrevious = Time("0.195")
                ),
                testParticipantResult(
                    position = 6,
                    participant = TestParticipants.Lscc2019Points1Simplified.BRYANT_MORAN,
                    runFns = listOf(
                        { participant ->
                            Run(
                                sequence = 26,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("56.353"),
                                cones = 1
                            ) to Score("46.040", Score.Penalty.Cone("2.000", 1), strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 27,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("55.831")
                            ) to Score("44.050", strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 28,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("52.201")
                            ) to Score("41.186", strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 29,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("52.062"),
                                cones = 1
                            ) to Score("42.654", Score.Penalty.Cone("2.000", 1), strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 30,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("53.074")
                            ) to Score("41.875", strict = false)
                        }
                    ),
                    score = Score("41.186", strict = false),
                    personalBestScoredRunIndex = 2,
                    diffFirst = Time("1.889"),
                    diffPrevious = Time("0.893")
                ),
                testParticipantResult(
                    position = 7,
                    participant = TestParticipants.Lscc2019Points1Simplified.DOMINIC_ROGERS,
                    runFns = listOf(
                        { participant ->
                            Run(
                                sequence = 31,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("54.246")
                            ) to Score("42.800", strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 32,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("53.629"),
                                cones = 1
                            ) to Score("43.891", Score.Penalty.Cone("2.000", 1), strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 33,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("51.856"),
                                cones = 1
                            ) to Score("42.492", Score.Penalty.Cone("2.000", 1), strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 34,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("53.409")
                            ) to Score("42.139", strict = false)
                        },
                        { participant ->
                            Run(
                                sequence = 35,
                                signage = participant.signage,
                                participant = participant,
                                time = Time("52.447")
                            ) to Score("41.380", strict = false)
                        }
                    ),
                    score = Score("41.380", strict = false),
                    personalBestScoredRunIndex = 4,
                    diffFirst = Time("2.083"),
                    diffPrevious = Time("0.194")
                )
            )
        )
    }

    object LifecyclePhases {
        private val participants = TestParticipants.LifecycleCases
        private val runs = TestRuns.LifecycleCases
        object Create {
            private val eventContexts = TestEventContexts.LifecycleCases.Create
            val noParticipantsYet by lazy {
                OverallEventResults(
                    eventContext = eventContexts.noParticipantsYet,
                    type = StandardEventResultsTypes.pax,
                    participantResults = emptyList()
                )
            }
            val runsWithoutSignage by lazy {
                OverallEventResults(
                    eventContext = eventContexts.runsWithoutSignage,
                    type = StandardEventResultsTypes.pax,
                    participantResults = emptyList()
                )
            }
            val runsWithoutParticipants by lazy {
                OverallEventResults(
                    eventContext = eventContexts.runsWithoutParticipants,
                    type = StandardEventResultsTypes.pax,
                    participantResults = emptyList()
                )
            }
            val participantsWithoutRuns by lazy {
                OverallEventResults(
                    eventContext = eventContexts.participantsWithoutRuns,
                    type = StandardEventResultsTypes.pax,
                    participantResults = emptyList()
                )
            }
            val someParticipantsWithSomeRuns by lazy {
                val runs = runs.someParticipantsWithSomeRuns
                OverallEventResults(
                    eventContext = eventContexts.someParticipantsWithSomeRuns,
                    type = StandardEventResultsTypes.pax,
                    participantResults = listOf(
                        testParticipantResult(
                            position = 1,
                            score = Score("26.962"),
                            participant = participants.REBECCA_JACKSON,
                            diffFirst = null,
                            diffPrevious = null,
                            personalBestScoredRunIndex = 0,
                            runFns = listOf { runs[0] to Score("26.962") }
                        )
                    )
                )
            }
            val someParticipantsWithAllRuns: OverallEventResults by lazy {
                val runs = runs.someParticipantsWithAllRuns
                OverallEventResults(
                    eventContext = eventContexts.someParticipantsWithAllRuns,
                    type = StandardEventResultsTypes.pax,
                    participantResults = listOf(
                        testParticipantResult(
                            position = 1,
                            score = Score("26.876"),
                            participant = participants.REBECCA_JACKSON,
                            diffFirst = null,
                            diffPrevious = null,
                            personalBestScoredRunIndex = 1,
                            runFns = listOf(
                                { runs[0] to Score("26.962") },
                                { runs[2] to Score("26.876") }
                            )
                        ),
                        testParticipantResult(
                            position = 2,
                            score = Score("29.506"),
                            participant = participants.JIMMY_MCKENZIE,
                            diffFirst = Time("2.630"),
                            diffPrevious = Time("2.630"),
                            personalBestScoredRunIndex = 0,
                            runFns = listOf { runs[1] to Score("29.506") }
                        )
                    )
                )
            }
            val allParticipantsWithSomeRuns: OverallEventResults by lazy {
                val runs = runs.allParticipantsWithSomeRuns
                OverallEventResults(
                    eventContext = eventContexts.allParticipantsWithSomeRuns,
                    type = StandardEventResultsTypes.pax,
                    participantResults = listOf(
                        testParticipantResult(
                            position = 1,
                            score = Score("26.962"),
                            participant = participants.REBECCA_JACKSON,
                            diffFirst = null,
                            diffPrevious = null,
                            personalBestScoredRunIndex = 0,
                            runFns = listOf { runs[0] to Score("26.962") }
                        ),
                        testParticipantResult(
                            position = 2,
                            score = Score("29.506"),
                            participant = participants.JIMMY_MCKENZIE,
                            diffFirst = Time("2.544"),
                            diffPrevious = Time("2.544"),
                            personalBestScoredRunIndex = 0,
                            runFns = listOf { runs[1] to Score("29.506") }
                        )
                    )
                )
            }
            val allParticipantsWithAllRuns: OverallEventResults by lazy {
                val runs = runs.allParticipantsWithAllRuns
                OverallEventResults(
                    eventContext = eventContexts.allParticipantsWithAllRuns,
                    type = StandardEventResultsTypes.pax,
                    participantResults = listOf(
                        testParticipantResult(
                            position = 1,
                            score = Score("26.876"),
                            participant = participants.REBECCA_JACKSON,
                            diffFirst = null,
                            diffPrevious = null,
                            personalBestScoredRunIndex = 1,
                            runFns = listOf(
                                { runs[0] to Score("26.962") },
                                { runs[2] to Score("26.876") }
                            )
                        ),
                        testParticipantResult(
                            position = 2,
                            score = Score("29.414"),
                            participant = participants.JIMMY_MCKENZIE,
                            diffFirst = Time("2.538"),
                            diffPrevious = Time("2.538"),
                            personalBestScoredRunIndex = 1,
                            runFns = listOf(
                                { runs[1] to Score("29.506") },
                                { runs[3] to Score("29.414") }
                            )
                        )
                    )
                )
            }
        }
    }
}