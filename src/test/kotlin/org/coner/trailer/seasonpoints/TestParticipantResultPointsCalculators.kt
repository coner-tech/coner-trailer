package org.coner.trailer.seasonpoints

object TestParticipantResultPointsCalculators {

    val lsccGroupingCalculator: ParticipantResultPointsCalculator
        get() = ParticipantResultPointsCalculator(
                positionToPoints = mapOf(
                        1 to 9,
                        2 to 6,
                        3 to 4,
                        4 to 3,
                        5 to 2
                ),
                defaultPoints = 1
        )

    val lsccOverallCalculator: ParticipantResultPointsCalculator
        get() = ParticipantResultPointsCalculator(
                positionToPoints = mapOf(
                        1 to 10,
                        2 to 9,
                        3 to 8,
                        4 to 7,
                        5 to 6,
                        6 to 5,
                        7 to 4,
                        8 to 3,
                        9 to 2,
                        10 to 1
                ),
                defaultPoints = 0
        )

    val olsccCalculator: ParticipantResultPointsCalculator
        get() = ParticipantResultPointsCalculator(
                didNotFinishPoints = 1,
                didNotStartPoints = 0,
                positionToPoints = mapOf(
                        1 to 20,
                        2 to 16,
                        3 to 13,
                        4 to 11,
                        5 to 9,
                        6 to 7,
                        7 to 5,
                        8 to 4,
                        9 to 3,
                        10 to 2
                ),
                defaultPoints = 1
        )
}