package org.coner.trailer.eventresults

import org.coner.trailer.*
import java.math.BigDecimal

object TestGroupedResultsReports {

    object Lscc2019 {

        private val policy = TestPolicies.lsccV1

        val points1 = listOf(
            GroupedResultsReport(
                type = StandardResultsTypes.grouped,
                groupingsToResultsMap = sortedMapOf(
                    TestGroupings.Lscc2019.NOV to listOf(
                        ParticipantResult(
                            position = 4,
                            score = Score("39.297"),
                            participant = TestParticipants.Lscc2019Points1.BRANDY_HUFF,
                            scoredRuns = listOf(
                                ResultRun(
                                    sequence = 1,
                                    score = Score("48.029", Score.Penalty.Cone("8.000", 4)),
                                    time = Time("49.419"),
                                    cones = 4
                                ),
                                ResultRun(
                                    sequence = 2,
                                    score = Score("46.377", Score.Penalty.Cone("6.000", 3)),
                                    time = Time("49.848"),
                                    cones = 3
                                ),
                                ResultRun(
                                    sequence = 3,
                                    score = Score("39.297"),
                                    time = Time("48.515")
                                ),
                                ResultRun(
                                    sequence = 4,
                                    score = Score("41.752", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("49.076"),
                                    cones = 1
                                ),
                                ResultRun(
                                    sequence = 5,
                                    score = Score("40.043"),
                                    time = Time("49.436")
                                )
                            ),
                            personalBestScoredRunIndex = 2,
                            diffPrevious = Time("0.045"),
                            diffFirst = Time("1.483")
                        ),
                        ParticipantResult(
                            position = 9,
                            score = Score("41.134"),
                            participant = TestParticipants.Lscc2019Points1.BRYANT_MORAN,
                            scoredRuns = listOf(
                                ResultRun(
                                    sequence = 6,
                                    score = Score("46.406", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("56.353"),
                                    cones = 1
                                ),
                                ResultRun(
                                    sequence = 7,
                                    score = Score("43.995"),
                                    time = Time("55.831")
                                ),
                                ResultRun(
                                    sequence = 8,
                                    score = Score("41.134"),
                                    time = Time("52.201")
                                ),
                                ResultRun(
                                    sequence = 9,
                                    score = Score("43.025", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("52.062"),
                                    cones = 1
                                ),
                                ResultRun(
                                    sequence = 10,
                                    score = Score("41.822"),
                                    time = Time("53.074")
                                )
                            ),
                            personalBestScoredRunIndex = 2,
                            diffFirst = Time("3.320"),
                            diffPrevious = Time("0.489")
                        ),
                        ParticipantResult(
                            position = 11,
                            score = Score("41.381"),
                            participant = TestParticipants.Lscc2019Points1.DOMINIC_ROGERS,
                            scoredRuns = listOf(
                                ResultRun(
                                    sequence = 11,
                                    score = Score("42.800"),
                                    time = Time("54.246")
                                ),
                                ResultRun(
                                    sequence = 12,
                                    score = Score("44.313", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("53.629"),
                                    cones = 1
                                ),
                                ResultRun(
                                    sequence = 13,
                                    score = Score("42.914", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("51.856"),
                                    cones = 1
                                ),
                                ResultRun(
                                    sequence = 14,
                                    score = Score("42.140"),
                                    time = Time("53.409")
                                ),
                                ResultRun(
                                    sequence = 15,
                                    score = Score("41.381"),
                                    time = Time("52.447")
                                )
                            ),
                            personalBestScoredRunIndex = 4,
                            diffFirst = Time("3.566"),
                            diffPrevious = Time("0.043")
                        )
                    ),
                    TestGroupings.Lscc2019.STR to listOf(
                        ParticipantResult(
                            position = 2,
                            score = Score("47.544"),
                            participant = TestParticipants.Lscc2019Points1.EUGENE_DRAKE,
                            scoredRuns = listOf(
                                ResultRun(
                                    sequence = 16,
                                    score = Score("49.367"),
                                    time = Time("49.367")
                                ),
                                ResultRun(
                                    sequence = 17,
                                    score = Score("49.230"),
                                    time = Time("49.230")
                                ),
                                ResultRun(
                                    sequence = 18,
                                    score = Score("48.807"),
                                    time = Time("48.807")
                                ),
                                ResultRun(
                                    sequence = 19,
                                    score = Score("214748413.573"),
                                    time = Time("49.573"),
                                    didNotFinish = true
                                ),
                                ResultRun(
                                    sequence = 20,
                                    score = Score("47.544"),
                                    time = Time("47.544")
                                )
                            ),
                            personalBestScoredRunIndex = 4,
                            diffFirst = Time("1.401"),
                            diffPrevious = Time("1.401")
                        ),
                        ParticipantResult(
                            position = 4,
                            score = Score("48.723"),
                            participant = TestParticipants.Lscc2019Points1.JIMMY_MCKENZIE,
                            scoredRuns = listOf(
                                ResultRun(
                                    sequence = 21,
                                    score = Score("54.115", Score.Penalty.Cone("4.000", 2)),
                                    time = Time("50.115"),
                                    cones = 2
                                ),
                                ResultRun(
                                    sequence = 22,
                                    score = Score("52.162", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("50.162"),
                                    cones = 1
                                ),
                                ResultRun(
                                    sequence = 23,
                                    score = Score("49.672"),
                                    time = Time("49.672")
                                ),
                                ResultRun(
                                    sequence = 24,
                                    score = Score("49.992"),
                                    time = Time("49.992")
                                ),
                                ResultRun(
                                    sequence = 25,
                                    score = Score("48.723"),
                                    time = Time("48.723")
                                )
                            ),
                            personalBestScoredRunIndex = 4,
                            diffFirst = Time("2.580"),
                            diffPrevious = Time("0.349")
                        )
                    ),
                    TestGroupings.Lscc2019.GS to listOf(
                        ParticipantResult(
                            position = 2,
                            score = Score("48.141"),
                            participant = TestParticipants.Lscc2019Points1.TERI_POTTER,
                            scoredRuns = listOf(
                                ResultRun(
                                    sequence = 26,
                                    score = Score("51.026"),
                                    time = Time("51.026")
                                ),
                                ResultRun(
                                    sequence = 27,
                                    score = Score("49.335"),
                                    time = Time("49.335")
                                ),
                                ResultRun(
                                    sequence = 28,
                                    score = Score("50.396", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("48.396"),
                                    cones = 1
                                ),
                                ResultRun(
                                    sequence = 29,
                                    score = Score("49.208"),
                                    time = Time("49.208")
                                ),
                                ResultRun(
                                    sequence = 30,
                                    score = Score("48.141"),
                                    time = Time("48.141")
                                )
                            ),
                            personalBestScoredRunIndex = 4,
                            diffFirst = Time("0.379"),
                            diffPrevious = Time("0.379")
                        ),
                        ParticipantResult(
                            position = 3,
                            score = Score("49.057"),
                            participant = TestParticipants.Lscc2019Points1.NORMAN_ROBINSON,
                            scoredRuns = listOf(
                                ResultRun(
                                    sequence = 31,
                                    score = Score("52.400"),
                                    time = Time("52.400")
                                ),
                                ResultRun(
                                    sequence = 32,
                                    score = Score("52.122"),
                                    time = Time("52.122")
                                ),
                                ResultRun(
                                    sequence = 33,
                                    score = Score("51.967"),
                                    time = Time("51.967")
                                ),
                                ResultRun(
                                    sequence = 34,
                                    score = Score("49.637"),
                                    time = Time("49.637")
                                ),
                                ResultRun(
                                    sequence = 35,
                                    score = Score("49.057"),
                                    time = Time("49.057")
                                )
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
            scoredRuns = listOf(ResultRun(
                sequence = 0,
                score = Score("0.000"),
                time = Time("0.000")
            )),
            personalBestScoredRunIndex = 0,
            diffFirst = null,
            diffPrevious = null
        )
    }
}