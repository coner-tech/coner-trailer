package org.coner.trailer.eventresults

import org.coner.trailer.TestGroupings
import org.coner.trailer.TestParticipants
import org.coner.trailer.Time

object TestGroupedResultsReports {

    val LSCC_2019_POINTS_1 = listOf(
            GroupedResultsReport(
                    type = StandardResultsTypes.competitionGrouped,
                    groupingsToResultsMap = mapOf(
                            TestGroupings.THSCC_2019_NOV to listOf(
                                    ParticipantResult(
                                            position = 4,
                                            participant = TestParticipants.Thscc2019Points1.BRANDY_HUFF,
                                            scoredRuns = listOf(
                                                    ResultRun(
                                                            time = Time("49.419"),
                                                            cones = 4
                                                    ),
                                                    ResultRun(
                                                            time = Time("49.848"),
                                                            cones = 3
                                                    ),
                                                    ResultRun(
                                                            time = Time("48.515"),
                                                            personalBest = true
                                                    ),
                                                    ResultRun(
                                                            time = Time("49.076"),
                                                            cones = 1
                                                    ),
                                                    ResultRun(
                                                            time = Time("49.436")
                                                    )
                                            ),
                                            marginOfVictory = Time("0.802"),
                                            marginOfLoss = Time("0.045")
                                    ),
                                    ParticipantResult(
                                            position = 9,
                                            participant = TestParticipants.Thscc2019Points1.BRYANT_MORAN,
                                            scoredRuns = listOf(
                                                    ResultRun(
                                                            time = Time("56.353"),
                                                            cones = 1
                                                    ),
                                                    ResultRun(
                                                            time = Time("55.831")
                                                    ),
                                                    ResultRun(
                                                            time = Time("52.201"),
                                                            personalBest = true
                                                    ),
                                                    ResultRun(
                                                            time = Time("52.062"),
                                                            cones = 1
                                                    ),
                                                    ResultRun(
                                                            time = Time("53.074")
                                                    )
                                            ),
                                            marginOfVictory = Time("0.203"),
                                            marginOfLoss = Time("0.489")
                                    ),
                                    ParticipantResult(
                                            position = 11,
                                            participant = TestParticipants.Thscc2019Points1.DOMINIC_ROGERS,
                                            scoredRuns = listOf(
                                                    ResultRun(
                                                            time = Time("54.246")
                                                    ),
                                                    ResultRun(
                                                            time = Time("53.629"),
                                                            cones = 1
                                                    ),
                                                    ResultRun(
                                                            time = Time("51.856"),
                                                            cones = 1
                                                    ),
                                                    ResultRun(
                                                            time = Time("53.409")
                                                    ),
                                                    ResultRun(
                                                            time = Time("52.447"),
                                                            personalBest = true
                                                    )
                                            ),
                                            marginOfVictory = Time("0.079"),
                                            marginOfLoss = Time("0.043")
                                    )
                            ),
                            TestGroupings.THSCC_2019_STR to listOf(
                                    ParticipantResult(
                                            position = 2,
                                            participant = TestParticipants.Thscc2019Points1.EUGENE_DRAKE,
                                            scoredRuns = listOf(
                                                    ResultRun(
                                                            time = Time("49.367")
                                                    ),
                                                    ResultRun(
                                                            time = Time("49.230")
                                                    ),
                                                    ResultRun(
                                                            time = Time("48.807")
                                                    ),
                                                    ResultRun(
                                                            time = Time("49.573"),
                                                            didNotFinish = true
                                                    ),
                                                    ResultRun(
                                                            time = Time("47.544"),
                                                            personalBest = true
                                                    )
                                            ),
                                            marginOfVictory = Time("0.830"),
                                            marginOfLoss = Time("1.401")
                                    ),
                                    ParticipantResult(
                                            position = 4,
                                            participant = TestParticipants.Thscc2019Points1.JIMMY_MCKENZIE,
                                            scoredRuns = listOf(
                                                    ResultRun(
                                                            time = Time("50.115"),
                                                            cones = 2
                                                    ),
                                                    ResultRun(
                                                            time = Time("50.162"),
                                                            cones = 1
                                                    ),
                                                    ResultRun(
                                                            time = Time("49.672")
                                                    ),
                                                    ResultRun(
                                                            time = Time("49.992")
                                                    ),
                                                    ResultRun(
                                                            time = Time("48.723"),
                                                            personalBest = true
                                                    )
                                            ),
                                            marginOfVictory = Time("0.633"),
                                            marginOfLoss = Time("0.349")
                                    )
                            ),
                            TestGroupings.THSCC_2019_GS to listOf(
                                    ParticipantResult(
                                            position = 2,
                                            participant = TestParticipants.Thscc2019Points1.TERI_POTTER,
                                            scoredRuns = listOf(
                                                    ResultRun(
                                                            time = Time("51.026")
                                                    ),
                                                    ResultRun(
                                                            time = Time("49.335")
                                                    ),
                                                    ResultRun(
                                                            time = Time("48.396"),
                                                            cones = 1
                                                    ),
                                                    ResultRun(
                                                            time = Time("49.208")
                                                    ),
                                                    ResultRun(
                                                            time = Time("48.141"),
                                                            personalBest = true
                                                    )
                                            ),
                                            marginOfVictory = Time("0.916"),
                                            marginOfLoss = Time("0.379")
                                    ),
                                    ParticipantResult(
                                            position = 3,
                                            participant = TestParticipants.Thscc2019Points1.NORMAN_ROBINSON,
                                            scoredRuns = listOf(
                                                    ResultRun(
                                                            time = Time("52.400")
                                                    ),
                                                    ResultRun(
                                                            time = Time("52.122")
                                                    ),
                                                    ResultRun(
                                                            time = Time("51.967")
                                                    ),
                                                    ResultRun(
                                                            time = Time("49.637")
                                                    ),
                                                    ResultRun(
                                                            time = Time("49.057"),
                                                            personalBest = true
                                                    )
                                            ),
                                            marginOfVictory = Time("0.453"),
                                            marginOfLoss = Time("0.916")
                                    )
                            )
                    )
            )
    )
}