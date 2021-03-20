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
                                    score = Score("48.029", Score.Penalty.Cone("8.000", 4)),
                                    time = Time("49.419"),
                                    cones = 4
                                ),
                                ResultRun(
                                    score = Score("46.377", Score.Penalty.Cone("6.000", 3)),
                                    time = Time("49.848"),
                                    cones = 3
                                ),
                                ResultRun(
                                    score = Score("39.297"),
                                    time = Time("48.515"),
                                    personalBest = true
                                ),
                                ResultRun(
                                    score = Score("41.752", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("49.076"),
                                    cones = 1
                                ),
                                ResultRun(
                                    score = Score("40.043"),
                                    time = Time("49.436")
                                )
                            ),
                            diffPrevious = Time("0.045"),
                            diffFirst = Time("1.483")
                        ),
                        ParticipantResult(
                            position = 9,
                            score = Score("41.134"),
                            participant = TestParticipants.Lscc2019Points1.BRYANT_MORAN,
                            scoredRuns = listOf(
                                ResultRun(
                                    score = Score("46.406", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("56.353"),
                                    cones = 1
                                ),
                                ResultRun(
                                    score = Score("43.995"),
                                    time = Time("55.831")
                                ),
                                ResultRun(
                                    score = Score("41.134"),
                                    time = Time("52.201"),
                                    personalBest = true
                                ),
                                ResultRun(
                                    score = Score("43.025", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("52.062"),
                                    cones = 1
                                ),
                                ResultRun(
                                    score = Score("41.822"),
                                    time = Time("53.074")
                                )
                            ),
                            diffFirst = Time("3.320"),
                            diffPrevious = Time("0.489")
                        ),
                        ParticipantResult(
                            position = 11,
                            score = Score("41.381"),
                            participant = TestParticipants.Lscc2019Points1.DOMINIC_ROGERS,
                            scoredRuns = listOf(
                                ResultRun(
                                    score = Score("42.800"),
                                    time = Time("54.246")
                                ),
                                ResultRun(
                                    score = Score("44.313", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("53.629"),
                                    cones = 1
                                ),
                                ResultRun(
                                    score = Score("42.914", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("51.856"),
                                    cones = 1
                                ),
                                ResultRun(
                                    score = Score("42.140"),
                                    time = Time("53.409")
                                ),
                                ResultRun(
                                    score = Score("41.381"),
                                    time = Time("52.447"),
                                    personalBest = true
                                )
                            ),
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
                                    score = Score("49.367"),
                                    time = Time("49.367")
                                ),
                                ResultRun(
                                    score = Score("49.230"),
                                    time = Time("49.230")
                                ),
                                ResultRun(
                                    score = Score("48.807"),
                                    time = Time("48.807")
                                ),
                                ResultRun(
                                    score = Score("214748413.573"),
                                    time = Time("49.573"),
                                    didNotFinish = true
                                ),
                                ResultRun(
                                    score = Score("47.544"),
                                    time = Time("47.544"),
                                    personalBest = true
                                )
                            ),
                            diffFirst = Time("1.401"),
                            diffPrevious = Time("1.401")
                        ),
                        ParticipantResult(
                            position = 4,
                            score = Score("48.723"),
                            participant = TestParticipants.Lscc2019Points1.JIMMY_MCKENZIE,
                            scoredRuns = listOf(
                                ResultRun(
                                    score = Score("54.115", Score.Penalty.Cone("4.000", 2)),
                                    time = Time("50.115"),
                                    cones = 2
                                ),
                                ResultRun(
                                    score = Score("52.162", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("50.162"),
                                    cones = 1
                                ),
                                ResultRun(
                                    score = Score("49.672"),
                                    time = Time("49.672")
                                ),
                                ResultRun(
                                    score = Score("49.992"),
                                    time = Time("49.992")
                                ),
                                ResultRun(
                                    score = Score("48.723"),
                                    time = Time("48.723"),
                                    personalBest = true
                                )
                            ),
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
                                    score = Score("51.026"),
                                    time = Time("51.026")
                                ),
                                ResultRun(
                                    score = Score("49.335"),
                                    time = Time("49.335")
                                ),
                                ResultRun(
                                    score = Score("50.396", Score.Penalty.Cone("2.000", 1)),
                                    time = Time("48.396"),
                                    cones = 1
                                ),
                                ResultRun(
                                    score = Score("49.208"),
                                    time = Time("49.208")
                                ),
                                ResultRun(
                                    score = Score("48.141"),
                                    time = Time("48.141"),
                                    personalBest = true
                                )
                            ),
                            diffFirst = Time("0.379"),
                            diffPrevious = Time("0.379")
                        ),
                        ParticipantResult(
                            position = 3,
                            score = Score("49.057"),
                            participant = TestParticipants.Lscc2019Points1.NORMAN_ROBINSON,
                            scoredRuns = listOf(
                                ResultRun(
                                    score = Score("52.400"),
                                    time = Time("52.400")
                                ),
                                ResultRun(
                                    score = Score("52.122"),
                                    time = Time("52.122")
                                ),
                                ResultRun(
                                    score = Score("51.967"),
                                    time = Time("51.967")
                                ),
                                ResultRun(
                                    score = Score("49.637"),
                                    time = Time("49.637")
                                ),
                                ResultRun(
                                    score = Score("49.057"),
                                    time = Time("49.057"),
                                    personalBest = true
                                )
                            ),
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
                score = Score("0.000"),
                time = Time("0.000"),
                personalBest = true
            )),
            diffFirst = null,
            diffPrevious = null
        )
    }
}