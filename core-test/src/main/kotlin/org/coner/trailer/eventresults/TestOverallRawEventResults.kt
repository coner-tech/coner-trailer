package org.coner.trailer.eventresults

import org.coner.trailer.Run
import org.coner.trailer.TestParticipants
import org.coner.trailer.Time
import java.math.BigDecimal

object TestOverallRawEventResults {
    object Lscc2019Simplified {
        val points1: OverallEventResults = OverallEventResults(
            type = StandardEventResultsTypes.raw,
            runCount = 5,
            participantResults = listOf(
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
                                didNotFinish = true
                            ) to Score("${Score.Penalty.DidNotFinish.floor.plus(BigDecimal("49.573"))}", Score.Penalty.DidNotFinish)
                        },
                        { participant ->
                            Run(
                                sequence = 5,
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
                                participant = participant,
                                time = Time("49.419"),
                                cones = 4
                            ) to Score("57.419", Score.Penalty.Cone("8.000", 4))
                        },
                        { participant ->
                            Run(
                                sequence = 22,
                                participant = participant,
                                time = Time("49.848"),
                                cones = 3
                            ) to Score("55.848", Score.Penalty.Cone("6.000", 3))
                        },
                        { participant ->
                            Run(
                                sequence = 23,
                                participant = participant,
                                time = Time("48.515")
                            ) to Score("48.515")
                        },
                        { participant ->
                            Run(
                                sequence = 24,
                                participant = participant,
                                time = Time("49.076"),
                                cones = 1
                            ) to Score("51.076", Score.Penalty.Cone("2.000", 1))
                        },
                        { participant ->
                            Run(
                                sequence = 25,
                                participant = participant,
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
                                participant = participant,
                                time = Time("50.115"),
                                cones = 2
                            ) to Score("54.115", Score.Penalty.Cone("4.000", 2))
                        },
                        { participant ->
                            Run(
                                sequence = 7,
                                participant = participant,
                                time = Time("50.162"),
                                cones = 1
                            ) to Score("52.162", Score.Penalty.Cone("2.000", 1))
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
                    diffPrevious = Time("0.208")
                ),
                testParticipantResult(
                    position = 4,
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
                            ) to Score("54.179", Score.Penalty.Cone("2.000", 1))
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
                                participant = participant,
                                time = Time("56.353"),
                                cones = 1
                            ) to Score("58.353", Score.Penalty.Cone("2.000", 1))
                        },
                        { participant ->
                            Run(
                                sequence = 27,
                                participant = participant,
                                time = Time("55.831")
                            ) to Score("55.831")
                        },
                        { participant ->
                            Run(
                                sequence = 28,
                                participant = participant,
                                time = Time("52.201")
                            ) to Score("52.201")
                        },
                        { participant ->
                            Run(
                                sequence = 29,
                                participant = participant,
                                time = Time("52.062"),
                                cones = 1
                            ) to Score("54.062", Score.Penalty.Cone("2.000", 1))
                        },
                        { participant ->
                            Run(
                                sequence = 30,
                                participant = participant,
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
                                participant = participant,
                                time = Time("54.246")
                            ) to Score("54.246")
                        },
                        { participant ->
                            Run(
                                sequence = 32,
                                participant = participant,
                                time = Time("53.629"),
                                cones = 1
                            ) to Score("55.629", Score.Penalty.Cone("2.000", 1))
                        },
                        { participant ->
                            Run(
                                sequence = 33,
                                participant = participant,
                                time = Time("51.856"),
                                cones = 1
                            ) to Score("53.856", Score.Penalty.Cone("2.000", 1))
                        },
                        { participant ->
                            Run(
                                sequence = 34,
                                participant = participant,
                                time = Time("53.409")
                            ) to Score("53.409")
                        },
                        { participant ->
                            Run(
                                sequence = 35,
                                participant = participant,
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
}