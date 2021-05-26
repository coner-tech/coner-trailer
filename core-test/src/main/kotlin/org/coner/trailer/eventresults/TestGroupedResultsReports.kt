package org.coner.trailer.eventresults

import org.coner.trailer.*

object TestGroupedResultsReports {

    object Lscc2019 {

        private val policy = TestPolicies.lsccV1

        val points1 = listOf(
            GroupedResultsReport(
                type = StandardResultsTypes.grouped,
                groupingsToResultsMap = sortedMapOf(
                    TestGroupings.Lscc2019.NOV to listOf(
                        testParticipantResult(
                            position = 4,
                            score = Score("39.297"),
                            participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                            scoredRunsFns = listOf(
                                { participant -> testResultRun(
                                    sequence = 1,
                                    participant = participant,
                                    time = Time("49.419"),
                                    cones = 4,
                                    score = Score("48.029", Score.Penalty.Cone("8.000", 4)),
                                ) },
                                { participant -> testResultRun(
                                    sequence = 2,
                                    participant = participant,
                                    score = Score("46.377", Score.Penalty.Cone("6.000", 3)),
                                    time = Time("49.848"),
                                    cones = 3
                                ) },
                                { participant -> testResultRun(
                                    sequence = 3,
                                    participant = participant,
                                    score = Score("39.297"),
                                    time = Time("48.515")
                                ) },
                                { participant -> testResultRun(
                                    sequence = 4,
                                    participant = participant,
                                    score = Score("41.752", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("49.076"),
                                    cones = 1
                                ) },
                                { participant -> testResultRun(
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
                            scoredRunsFns = listOf(
                                { participant -> testResultRun(
                                    sequence = 6,
                                    participant = participant,
                                    score = Score("46.406", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("56.353"),
                                    cones = 1
                                ) },
                                { participant -> testResultRun(
                                    sequence = 7,
                                    participant = participant,
                                    score = Score("43.995"),
                                    time = Time("55.831")
                                ) },
                                { participant -> testResultRun(
                                    sequence = 8,
                                    participant = participant,
                                    score = Score("41.134"),
                                    time = Time("52.201")
                                ) },
                                { participant -> testResultRun(
                                    sequence = 9,
                                    participant = participant,
                                    score = Score("43.025", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("52.062"),
                                    cones = 1
                                ) },
                                { participant -> testResultRun(
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
                            scoredRunsFns = listOf(
                                { participant -> testResultRun(
                                    sequence = 11,
                                    participant = participant,
                                    score = Score("42.800"),
                                    time = Time("54.246")
                                ) },
                                { participant -> testResultRun(
                                    sequence = 12,
                                    participant = participant,
                                    score = Score("44.313", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("53.629"),
                                    cones = 1
                                ) },
                                { participant -> testResultRun(
                                    sequence = 13,
                                    participant = participant,
                                    score = Score("42.914", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("51.856"),
                                    cones = 1
                                ) },
                                { participant -> testResultRun(
                                    sequence = 14,
                                    participant = participant,
                                    score = Score("42.140"),
                                    time = Time("53.409")
                                ) },
                                { participant -> testResultRun(
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
                    TestGroupings.Lscc2019.STR to listOf(
                        testParticipantResult(
                            position = 2,
                            score = Score("47.544"),
                            participant = TestParticipants.Lscc2019Points1.EUGENE_DRAKE,
                            scoredRunsFns = listOf(
                                { participant -> testResultRun(
                                    sequence = 16,
                                    participant = participant,
                                    score = Score("49.367"),
                                    time = Time("49.367")
                                ) },
                                { participant -> testResultRun(
                                    sequence = 17,
                                    participant = participant,
                                    score = Score("49.230"),
                                    time = Time("49.230")
                                ) },
                                { participant -> testResultRun(
                                    sequence = 18,
                                    participant = participant,
                                    score = Score("48.807"),
                                    time = Time("48.807")
                                ) },
                                { participant -> testResultRun(
                                    sequence = 19,
                                    participant = participant,
                                    score = Score("214748413.573"),
                                    time = Time("49.573"),
                                    didNotFinish = true
                                ) },
                                { participant -> testResultRun(
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
                            scoredRunsFns = listOf(
                                { participant -> testResultRun(
                                    sequence = 21,
                                    participant = participant,
                                    score = Score("54.115", Score.Penalty.Cone("4.000", 2)),
                                    time = Time("50.115"),
                                    cones = 2
                                ) },
                                { participant -> testResultRun(
                                    sequence = 22,
                                    participant = participant,
                                    score = Score("52.162", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("50.162"),
                                    cones = 1
                                ) },
                                { participant -> testResultRun(
                                    sequence = 23,
                                    participant = participant,
                                    score = Score("49.672"),
                                    time = Time("49.672")
                                ) },
                                { participant -> testResultRun(
                                    sequence = 24,
                                    participant = participant,
                                    score = Score("49.992"),
                                    time = Time("49.992")
                                ) },
                                { participant -> testResultRun(
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
                    TestGroupings.Lscc2019.GS to listOf(
                        testParticipantResult(
                            position = 2,
                            score = Score("48.141"),
                            participant = TestParticipants.Lscc2019Points1.TERI_POTTER,
                            scoredRunsFns = listOf(
                                { participant -> testResultRun(
                                    sequence = 26,
                                    participant = participant,
                                    score = Score("51.026"),
                                    time = Time("51.026")
                                ) },
                                { participant -> testResultRun(
                                    sequence = 27,
                                    participant = participant,
                                    score = Score("49.335"),
                                    time = Time("49.335")
                                ) },
                                { participant -> testResultRun(
                                    sequence = 28,
                                    participant = participant,
                                    score = Score("50.396", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("48.396"),
                                    cones = 1
                                ) },
                                { participant -> testResultRun(
                                    sequence = 29,
                                    participant = participant,
                                    score = Score("49.208"),
                                    time = Time("49.208")
                                ) },
                                { participant -> testResultRun(
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
                            scoredRunsFns = listOf(
                                { participant -> testResultRun(
                                    sequence = 31,
                                    participant = participant,
                                    score = Score("52.400"),
                                    time = Time("52.400")
                                ) },
                                { participant -> testResultRun(
                                    sequence = 32,
                                    participant = participant,
                                    score = Score("52.122"),
                                    time = Time("52.122")
                                ) },
                                { participant -> testResultRun(
                                    sequence = 33,
                                    participant = participant,
                                    score = Score("51.967"),
                                    time = Time("51.967")
                                ) },
                                { participant -> testResultRun(
                                    sequence = 34,
                                    participant = participant,
                                    score = Score("49.637"),
                                    time = Time("49.637")
                                ) },
                                { participant -> testResultRun(
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
                )
            )
        )

    }

    object LsccTieBreaking {
        val points1: List<GroupedResultsReport>
            get() = listOf(
                GroupedResultsReport(
                    type = StandardResultsTypes.grouped,
                    groupingsToResultsMap = sortedMapOf(
                        TestGroupings.Lscc2019.HS to listOf(
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
                    )
                )
            )
        val points2: List<GroupedResultsReport>
            get() = listOf(
                GroupedResultsReport(
                    type = StandardResultsTypes.grouped,
                    groupingsToResultsMap = sortedMapOf(
                        TestGroupings.Lscc2019.HS to listOf(
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
                    )
                )
            )

        private fun tieBreakingParticipantResult(position: Int, participant: Participant) = ParticipantResult(
            position = position,
            participant = participant,
            // below properties irrelevant to test:
            score = Score("0.000"),
            scoredRuns = listOf(testResultRun(
                sequence = 0,
                participant = participant,
                score = Score("0.000"),
                time = Time("0.000")
            )),
            personalBestScoredRunIndex = 0,
            diffFirst = null,
            diffPrevious = null
        )
    }
}
