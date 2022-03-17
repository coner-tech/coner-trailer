package tech.coner.trailer.eventresults

import tech.coner.trailer.*

object TestClazzEventResults {

    object Lscc2019 {

        private val policy = TestPolicies.lsccV1

        val points1 = listOf(
            GroupEventResults(
                type = StandardEventResultsTypes.clazz,
                groupParticipantResults = sortedMapOf(
                    TestClasses.Lscc2019.NOV to listOf(
                        testParticipantResult(
                            position = 4,
                            score = Score("39.297"),
                            participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                            runFns = listOf(
                                { participant -> testRunWithScore(
                                    sequence = 1,
                                    participant = participant,
                                    time = Time("49.419"),
                                    score = Score("48.029", Score.Penalty.Cone("8.000", 4))
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 2,
                                    participant = participant,
                                    time = Time("49.848"),
                                    cones = 3,
                                    score = Score("46.377", Score.Penalty.Cone("6.000", 3))
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 3,
                                    participant = participant,
                                    score = Score("39.297"),
                                    time = Time("48.515")
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 4,
                                    participant = participant,
                                    score = Score("41.752", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("49.076"),
                                    cones = 1
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 5,
                                    participant = participant,
                                    score = Score("40.043"),
                                    time = Time("49.436")
                                ) }
                            ),
                            personalBestScoredRunIndex = 2,
                            diffPrevious = Time("0.045"),
                            diffFirst = Time("1.483")
                        ),
                        testParticipantResult(
                            position = 9,
                            score = Score("41.134"),
                            participant = TestParticipants.Lscc2019Points1.BRYANT_MORAN,
                            runFns = listOf(
                                { participant -> testRunWithScore(
                                    sequence = 6,
                                    participant = participant,
                                    score = Score("46.406", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("56.353"),
                                    cones = 1
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 7,
                                    participant = participant,
                                    score = Score("43.995"),
                                    time = Time("55.831")
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 8,
                                    participant = participant,
                                    score = Score("41.134"),
                                    time = Time("52.201")
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 9,
                                    participant = participant,
                                    score = Score("43.025", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("52.062"),
                                    cones = 1
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 10,
                                    participant = participant,
                                    score = Score("41.822"),
                                    time = Time("53.074")
                                ) }
                            ),
                            personalBestScoredRunIndex = 2,
                            diffFirst = Time("3.320"),
                            diffPrevious = Time("0.489")
                        ),
                        testParticipantResult(
                            position = 11,
                            score = Score("41.381"),
                            participant = TestParticipants.Lscc2019Points1.DOMINIC_ROGERS,
                            runFns = listOf(
                                { participant -> testRunWithScore(
                                    sequence = 11,
                                    participant = participant,
                                    score = Score("42.800"),
                                    time = Time("54.246")
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 12,
                                    participant = participant,
                                    score = Score("44.313", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("53.629"),
                                    cones = 1
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 13,
                                    participant = participant,
                                    score = Score("42.914", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("51.856"),
                                    cones = 1
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 14,
                                    participant = participant,
                                    score = Score("42.140"),
                                    time = Time("53.409")
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 15,
                                    participant = participant,
                                    score = Score("41.381"),
                                    time = Time("52.447")
                                ) }
                            ),
                            personalBestScoredRunIndex = 4,
                            diffFirst = Time("3.566"),
                            diffPrevious = Time("0.043")
                        )
                    ),
                    TestClasses.Lscc2019.STR to listOf(
                        testParticipantResult(
                            position = 2,
                            score = Score("47.544"),
                            participant = TestParticipants.Lscc2019Points1.EUGENE_DRAKE,
                            runFns = listOf(
                                { participant -> testRunWithScore(
                                    sequence = 16,
                                    participant = participant,
                                    score = Score("49.367"),
                                    time = Time("49.367")
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 17,
                                    participant = participant,
                                    score = Score("49.230"),
                                    time = Time("49.230")
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 18,
                                    participant = participant,
                                    score = Score("48.807"),
                                    time = Time("48.807")
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 19,
                                    participant = participant,
                                    score = Score("214748413.573"),
                                    time = Time("49.573"),
                                    didNotFinish = true
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 20,
                                    participant = participant,
                                    score = Score("47.544"),
                                    time = Time("47.544")
                                ) }
                            ),
                            personalBestScoredRunIndex = 4,
                            diffFirst = Time("1.401"),
                            diffPrevious = Time("1.401")
                        ),
                        testParticipantResult(
                            position = 4,
                            score = Score("48.723"),
                            participant = TestParticipants.Lscc2019Points1.JIMMY_MCKENZIE,
                            runFns = listOf(
                                { participant -> testRunWithScore(
                                    sequence = 21,
                                    participant = participant,
                                    score = Score("54.115", Score.Penalty.Cone("4.000", 2)),
                                    time = Time("50.115"),
                                    cones = 2
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 22,
                                    participant = participant,
                                    score = Score("52.162", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("50.162"),
                                    cones = 1
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 23,
                                    participant = participant,
                                    score = Score("49.672"),
                                    time = Time("49.672")
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 24,
                                    participant = participant,
                                    score = Score("49.992"),
                                    time = Time("49.992")
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 25,
                                    participant = participant,
                                    score = Score("48.723"),
                                    time = Time("48.723")
                                ) }
                            ),
                            personalBestScoredRunIndex = 4,
                            diffFirst = Time("2.580"),
                            diffPrevious = Time("0.349")
                        )
                    ),
                    TestClasses.Lscc2019.GS to listOf(
                        testParticipantResult(
                            position = 2,
                            score = Score("48.141"),
                            participant = TestParticipants.Lscc2019Points1.TERI_POTTER,
                            runFns = listOf(
                                { participant -> testRunWithScore(
                                    sequence = 26,
                                    participant = participant,
                                    score = Score("51.026"),
                                    time = Time("51.026")
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 27,
                                    participant = participant,
                                    score = Score("49.335"),
                                    time = Time("49.335")
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 28,
                                    participant = participant,
                                    score = Score("50.396", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("48.396"),
                                    cones = 1
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 29,
                                    participant = participant,
                                    score = Score("49.208"),
                                    time = Time("49.208")
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 30,
                                    participant = participant,
                                    score = Score("48.141"),
                                    time = Time("48.141")
                                ) }
                            ),
                            personalBestScoredRunIndex = 4,
                            diffFirst = Time("0.379"),
                            diffPrevious = Time("0.379")
                        ),
                        testParticipantResult(
                            position = 3,
                            score = Score("49.057"),
                            participant = TestParticipants.Lscc2019Points1.NORMAN_ROBINSON,
                            runFns = listOf(
                                { participant -> testRunWithScore(
                                    sequence = 31,
                                    participant = participant,
                                    score = Score("52.400"),
                                    time = Time("52.400")
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 32,
                                    participant = participant,
                                    score = Score("52.122"),
                                    time = Time("52.122")
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 33,
                                    participant = participant,
                                    score = Score("51.967"),
                                    time = Time("51.967")
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 34,
                                    participant = participant,
                                    score = Score("49.637"),
                                    time = Time("49.637")
                                ) },
                                { participant -> testRunWithScore(
                                    sequence = 35,
                                    participant = participant,
                                    score = Score("49.057"),
                                    time = Time("49.057")
                                ) }
                            ),
                            personalBestScoredRunIndex = 4,
                            diffFirst = Time("1.295"),
                            diffPrevious = Time("0.916")
                        )
                    )
                ),
                runCount = 5,
                parentClassTopTimes = listOf(
                    GroupEventResults.ParentClassTopTime(TestClasses.Lscc2019.STREET, testParticipantResult(
                        position = 1,
                        score = Score("48.141"),
                        participant = TestParticipants.Lscc2019Points1.TERI_POTTER,
                        runFns = listOf(
                            { participant -> testRunWithScore(
                                sequence = 26,
                                participant = participant,
                                score = Score("51.026"),
                                time = Time("51.026")
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 27,
                                participant = participant,
                                score = Score("49.335"),
                                time = Time("49.335")
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 28,
                                participant = participant,
                                score = Score("50.396", Score.Penalty.Cone("2.000", 1)),
                                time = Time("48.396"),
                                cones = 1
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 29,
                                participant = participant,
                                score = Score("49.208"),
                                time = Time("49.208")
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 30,
                                participant = participant,
                                score = Score("48.141"),
                                time = Time("48.141")
                            ) }
                        ),
                        personalBestScoredRunIndex = 4,
                        diffFirst = Time("0.379"),
                        diffPrevious = Time("0.379")
                    )),
                    GroupEventResults.ParentClassTopTime(TestClasses.Lscc2019.STREET_TOURING, testParticipantResult(
                        position = 2,
                        score = Score("47.544"),
                        participant = TestParticipants.Lscc2019Points1.EUGENE_DRAKE,
                        runFns = listOf(
                            { participant -> testRunWithScore(
                                sequence = 16,
                                participant = participant,
                                score = Score("49.367"),
                                time = Time("49.367")
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 17,
                                participant = participant,
                                score = Score("49.230"),
                                time = Time("49.230")
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 18,
                                participant = participant,
                                score = Score("48.807"),
                                time = Time("48.807")
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 19,
                                participant = participant,
                                score = Score("214748413.573"),
                                time = Time("49.573"),
                                didNotFinish = true
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 20,
                                participant = participant,
                                score = Score("47.544"),
                                time = Time("47.544")
                            ) }
                        ),
                        personalBestScoredRunIndex = 4,
                        diffFirst = Time("1.401"),
                        diffPrevious = Time("1.401")
                    ))
                )
            )
        )

    }

    object Lscc2019Simplified {
        val points1: GroupEventResults = GroupEventResults(
            type = StandardEventResultsTypes.clazz,
            runCount = 5,
            groupParticipantResults = sortedMapOf(
                TestClasses.Lscc2019.HS to listOf(
                    testParticipantResult(
                        position = 1,
                        participant = TestParticipants.Lscc2019Points1Simplified.ANASTASIA_RIGLER,
                        runFns = listOf(
                            { participant ->
                                Run(
                                    sequence = 16,
                                    participant = participant,
                                    time = Time("53.693")
                                ) to Score("53.693")
                            },
                            { participant ->
                                Run(
                                    sequence = 17,
                                    participant = participant,
                                    time = Time("52.179"),
                                    cones = 1
                                ) to Score("54.179", Score.Penalty.Cone("2", 1))
                            },
                            { participant ->
                                Run(
                                    sequence = 18,
                                    participant = participant,
                                    time = Time("52.256")
                                ) to Score("52.256")
                            },
                            { participant ->
                                Run(
                                    sequence = 19,
                                    participant = participant,
                                    time = Time("51.323")
                                ) to Score("51.323")
                            },
                            { participant ->
                                Run(
                                    sequence = 20,
                                    participant = participant,
                                    time = Time("51.344")
                                ) to Score("51.344")
                            }
                        ),
                        score = Score("51.323"),
                        personalBestScoredRunIndex = 3,
                        diffFirst = null,
                        diffPrevious = null
                    ),
                    testParticipantResult(
                        position = 2,
                        participant = TestParticipants.Lscc2019Points1Simplified.REBECCA_JACKSON,
                        runFns = listOf(
                            { participant ->
                                Run(
                                    sequence = 11,
                                    participant = participant,
                                    time = Time("52.749")
                                ) to Score("52.749")
                            },
                            { participant ->
                                Run(
                                    sequence = 12,
                                    participant = participant,
                                    time = Time("53.175")
                                ) to Score("53.175")
                            },
                            { participant ->
                                Run(
                                    sequence = 13,
                                    participant = participant,
                                    time = Time("52.130")
                                ) to Score("52.130")
                            },
                            { participant ->
                                Run(
                                    sequence = 14,
                                    participant = participant,
                                    time = Time("52.117")
                                ) to Score("52.117")
                            },
                            { participant ->
                                Run(
                                    sequence = 15,
                                    participant = participant,
                                    time = Time("51.408")
                                ) to Score("51.408")
                            }
                        ),
                        score = Score("51.408"),
                        personalBestScoredRunIndex = 4,
                        diffFirst = Time("0.085"),
                        diffPrevious = Time("0.085")
                    )
                ),
                TestClasses.Lscc2019.STR to listOf(
                    testParticipantResult(
                        position = 1,
                        participant = TestParticipants.Lscc2019Points1Simplified.EUGENE_DRAKE,
                        runFns = listOf(
                            { participant ->
                                Run(
                                    sequence = 1,
                                    participant = participant,
                                    time = Time("49.367")
                                ) to Score("49.367")
                            },
                            { participant ->
                                Run(
                                    sequence = 2,
                                    participant = participant,
                                    time = Time("49.230")
                                ) to Score("49.230")
                            },
                            { participant ->
                                Run(
                                    sequence = 3,
                                    participant = participant,
                                    time = Time("48.807")
                                ) to Score("48.807")
                            },
                            { participant ->
                                Run(
                                    sequence = 4,
                                    participant = participant,
                                    time = Time("49.573"),
                                    didNotFinish = true,
                                ) to Score("${Score.Penalty.DidNotFinish.floor}", Score.Penalty.DidNotFinish)
                            },
                            { participant ->
                                Run(
                                    sequence = 5,
                                    participant = participant,
                                    time = Time("47.544")
                                ) to Score("47.544")
                            }
                        ),
                        score = Score("47.544"),
                        personalBestScoredRunIndex = 4,
                        diffFirst = null,
                        diffPrevious = null
                    ),
                    testParticipantResult(
                        position = 2,
                        participant = TestParticipants.Lscc2019Points1Simplified.JIMMY_MCKENZIE,
                        runFns = listOf(
                            { participant ->
                                Run(
                                    sequence = 6,
                                    participant = participant,
                                    time = Time("50.115"),
                                    cones = 2
                                ) to Score("54.115", Score.Penalty.Cone("2", 2))
                            },
                            { participant ->
                                Run(
                                    sequence = 7,
                                    participant = participant,
                                    time = Time("50.162"),
                                    cones = 1
                                ) to Score("52.162", Score.Penalty.Cone("2", 1))
                            },
                            { participant ->
                                Run(
                                    sequence = 8,
                                    participant = participant,
                                    time = Time("49.672")
                                ) to Score("49.672")
                            },
                            { participant ->
                                Run(
                                    sequence = 9,
                                    participant = participant,
                                    time = Time("49.992")
                                ) to Score("49.992")
                            },
                            { participant ->
                                Run(
                                    sequence = 10,
                                    participant = participant,
                                    time = Time("48.723")
                                ) to Score("48.723")
                            }
                        ),
                        score = Score("48.723"),
                        personalBestScoredRunIndex = 4,
                        diffFirst = Time("1.179"),
                        diffPrevious = Time("1.179")
                    )
                ),
                TestClasses.Lscc2019.NOV to listOf(
                    testParticipantResult(
                        position = 1,
                        participant = TestParticipants.Lscc2019Points1Simplified.BRANDY_HUFF,
                        runFns = listOf(
                            { participant ->
                                Run(
                                    sequence = 21,
                                    participant = participant,
                                    time = Time("49.419"),
                                    cones = 4
                                ) to Score("46.509", Score.Penalty.Cone("2", 4))
                            },
                            { participant ->
                                Run(
                                    sequence = 22,
                                    participant = participant,
                                    time = Time("49.848"),
                                    cones = 3
                                ) to Score("45.236", Score.Penalty.Cone("2", 3))
                            },
                            { participant ->
                                Run(
                                    sequence = 23,
                                    participant = participant,
                                    time = Time("48.515")
                                ) to Score("39.297")
                            },
                            { participant ->
                                Run(
                                    sequence = 24,
                                    participant = participant,
                                    time = Time("49.076"),
                                    cones = 1
                                ) to Score("41.371", Score.Penalty.Cone("2", 1))
                            },
                            { participant ->
                                Run(
                                    sequence = 25,
                                    participant = participant,
                                    time = Time("49.436")
                                ) to Score("40.043")
                            }
                        ),
                        score = Score("39.297"),
                        personalBestScoredRunIndex = 2,
                        diffFirst = null,
                        diffPrevious = null
                    ),
                    testParticipantResult(
                        position = 2,
                        participant = TestParticipants.Lscc2019Points1Simplified.BRYANT_MORAN,
                        runFns = listOf(
                            { participant ->
                                Run(
                                    sequence = 26,
                                    participant = participant,
                                    time = Time("56.353"),
                                    cones = 1
                                ) to Score("46.040", Score.Penalty.Cone("2", 1))
                            },
                            { participant ->
                                Run(
                                    sequence = 27,
                                    participant = participant,
                                    time = Time("55.831")
                                ) to Score("44.050")
                            },
                            { participant ->
                                Run(
                                    sequence = 28,
                                    participant = participant,
                                    time = Time("52.201")
                                ) to Score("41.186")
                            },
                            { participant ->
                                Run(
                                    sequence = 29,
                                    participant = participant,
                                    time = Time("52.062"),
                                    cones = 1
                                ) to Score("42.654", Score.Penalty.Cone("2", 1))
                            },
                            { participant ->
                                Run(
                                    sequence = 30,
                                    participant = participant,
                                    time = Time("53.074")
                                ) to Score("41.875")
                            }
                        ),
                        score = Score("41.186"),
                        personalBestScoredRunIndex = 2,
                        diffFirst = Time("1.889"),
                        diffPrevious = Time("1.889")
                    ),
                    testParticipantResult(
                        position = 3,
                        participant = TestParticipants.Lscc2019Points1Simplified.DOMINIC_ROGERS,
                        runFns = listOf(
                            { participant ->
                                Run(
                                    sequence = 31,
                                    participant = participant,
                                    time = Time("54.246")
                                ) to Score("42.800")
                            },
                            { participant ->
                                Run(
                                    sequence = 32,
                                    participant = participant,
                                    time = Time("53.629"),
                                    cones = 1
                                ) to Score("43.891", Score.Penalty.Cone("2", 1))
                            },
                            { participant ->
                                Run(
                                    sequence = 33,
                                    participant = participant,
                                    time = Time("51.856"),
                                    cones = 1
                                ) to Score("42.492", Score.Penalty.Cone("2", 1))
                            },
                            { participant ->
                                Run(
                                    sequence = 34,
                                    participant = participant,
                                    time = Time("53.409")
                                ) to Score("42.139")
                            },
                            { participant ->
                                Run(
                                    sequence = 35,
                                    participant = participant,
                                    time = Time("52.447")
                                ) to Score("41.380")
                            }
                        ),
                        score = Score("41.380"),
                        personalBestScoredRunIndex = 4,
                        diffFirst = Time("2.083"),
                        diffPrevious = Time("0.194")
                    )
                )
            ),
            parentClassTopTimes = listOf(
                GroupEventResults.ParentClassTopTime(
                    parent = TestClasses.Lscc2019.STREET,
                    participantResult = testParticipantResult(
                        position = 1,
                        participant = TestParticipants.Lscc2019Points1Simplified.ANASTASIA_RIGLER,
                        runFns = listOf(
                            { participant ->
                                Run(
                                    sequence = 16,
                                    participant = participant,
                                    time = Time("53.693")
                                ) to Score("53.693")
                            },
                            { participant ->
                                Run(
                                    sequence = 17,
                                    participant = participant,
                                    time = Time("52.179"),
                                    cones = 1
                                ) to Score("54.179", Score.Penalty.Cone("2", 1))
                            },
                            { participant ->
                                Run(
                                    sequence = 18,
                                    participant = participant,
                                    time = Time("52.256")
                                ) to Score("52.256")
                            },
                            { participant ->
                                Run(
                                    sequence = 19,
                                    participant = participant,
                                    time = Time("51.323")
                                ) to Score("51.323")
                            },
                            { participant ->
                                Run(
                                    sequence = 20,
                                    participant = participant,
                                    time = Time("51.344")
                                ) to Score("51.344")
                            }
                        ),
                        score = Score("51.323"),
                        personalBestScoredRunIndex = 3,
                        diffFirst = null,
                        diffPrevious = null
                    )
                ),
                GroupEventResults.ParentClassTopTime(
                    parent = TestClasses.Lscc2019.STREET_TOURING,
                    participantResult = testParticipantResult(
                        position = 2,
                        score = Score("47.544"),
                        participant = TestParticipants.Lscc2019Points1.EUGENE_DRAKE,
                        runFns = listOf(
                            { participant -> testRunWithScore(
                                sequence = 16,
                                participant = participant,
                                score = Score("49.367"),
                                time = Time("49.367")
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 17,
                                participant = participant,
                                score = Score("49.230"),
                                time = Time("49.230")
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 18,
                                participant = participant,
                                score = Score("48.807"),
                                time = Time("48.807")
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 19,
                                participant = participant,
                                score = Score("214748413.573"),
                                time = Time("49.573"),
                                didNotFinish = true
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 20,
                                participant = participant,
                                score = Score("47.544"),
                                time = Time("47.544")
                            ) }
                        ),
                        personalBestScoredRunIndex = 4,
                        diffFirst = Time("1.401"),
                        diffPrevious = Time("1.401")
                    )
                ),
                GroupEventResults.ParentClassTopTime(
                    parent = TestClasses.Lscc2019.NOVICE,
                    participantResult = testParticipantResult(
                        position = 4,
                        score = Score("39.297"),
                        participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                        runFns = listOf(
                            { participant -> testRunWithScore(
                                sequence = 1,
                                participant = participant,
                                time = Time("49.419"),
                                score = Score("48.029", Score.Penalty.Cone("8.000", 4))
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 2,
                                participant = participant,
                                time = Time("49.848"),
                                cones = 3,
                                score = Score("46.377", Score.Penalty.Cone("6.000", 3))
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 3,
                                participant = participant,
                                score = Score("39.297"),
                                time = Time("48.515")
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 4,
                                participant = participant,
                                score = Score("41.752", Score.Penalty.Cone("2.000", 1)),
                                time = Time("49.076"),
                                cones = 1
                            ) },
                            { participant -> testRunWithScore(
                                sequence = 5,
                                participant = participant,
                                score = Score("40.043"),
                                time = Time("49.436")
                            ) }
                        ),
                        personalBestScoredRunIndex = 2,
                        diffPrevious = Time("0.045"),
                        diffFirst = Time("1.483")
                    )
                )
            )
        )
    }

    object LsccTieBreaking {
        val points1: List<GroupEventResults>
            get() = listOf(
                GroupEventResults(
                    type = StandardEventResultsTypes.clazz,
                    groupParticipantResults = sortedMapOf(
                        TestClasses.Lscc2019.HS to listOf(
                            tieBreakingParticipantResult(
                                position = 1,
                                participant = TestParticipants.LsccTieBreaking.REBECCA_JACKSON
                            ),
                            tieBreakingParticipantResult(
                                position = 2,
                                participant = TestParticipants.LsccTieBreaking.JIMMY_MCKENZIE
                            ),
                            tieBreakingParticipantResult(
                                position = 3,
                                participant = TestParticipants.LsccTieBreaking.EUGENE_DRAKE
                            ),
                            tieBreakingParticipantResult(
                                position = 4,
                                participant = TestParticipants.LsccTieBreaking.TERI_POTTER
                            ),
                            tieBreakingParticipantResult(
                                position = 5,
                                participant = TestParticipants.LsccTieBreaking.HARRY_WEBSTER
                            )
                        )
                    ),
                    runCount = 1,
                    parentClassTopTimes = listOf(
                        GroupEventResults.ParentClassTopTime(TestClasses.Lscc2019.STREET, tieBreakingParticipantResult(
                            position = 1,
                            participant = TestParticipants.LsccTieBreaking.REBECCA_JACKSON
                        )
                    ))
                )
            )
        val points2: List<GroupEventResults>
            get() = listOf(
                GroupEventResults(
                    type = StandardEventResultsTypes.clazz,
                    groupParticipantResults = sortedMapOf(
                        TestClasses.Lscc2019.HS to listOf(
                            tieBreakingParticipantResult(
                                position = 1,
                                participant = TestParticipants.LsccTieBreaking.JIMMY_MCKENZIE
                            ),
                            tieBreakingParticipantResult(
                                position = 2,
                                participant = TestParticipants.LsccTieBreaking.REBECCA_JACKSON
                            ),
                            tieBreakingParticipantResult(
                                position = 3,
                                participant = TestParticipants.LsccTieBreaking.EUGENE_DRAKE
                            ),
                            tieBreakingParticipantResult(
                                position = 4,
                                participant = TestParticipants.LsccTieBreaking.HARRY_WEBSTER
                            ),
                            tieBreakingParticipantResult(
                                position = 5,
                                participant = TestParticipants.LsccTieBreaking.TERI_POTTER
                            )
                        )
                    ),
                    runCount = 1,
                    parentClassTopTimes = listOf(
                        GroupEventResults.ParentClassTopTime(TestClasses.Lscc2019.STREET, tieBreakingParticipantResult(
                            position = 1,
                            participant = TestParticipants.LsccTieBreaking.JIMMY_MCKENZIE
                        )
                    ))
                )
            )

        private fun tieBreakingParticipantResult(position: Int, participant: Participant) = testParticipantResult(
            position = position,
            participant = participant,
            // below properties irrelevant to test:
            score = Score("0.000"),
            runFns = listOf { _ ->
                testRunWithScore(
                    sequence = 0,
                    participant = participant,
                    score = Score("0.000"),
                    time = Time("0.000")
                )
            },
            personalBestScoredRunIndex = 0,
            diffFirst = null,
            diffPrevious = null
        )
    }
}
