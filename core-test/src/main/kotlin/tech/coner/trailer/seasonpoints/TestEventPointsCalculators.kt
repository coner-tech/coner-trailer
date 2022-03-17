package tech.coner.trailer.seasonpoints

object TestEventPointsCalculators {

    val lsccGroupedCalculator by lazy { EventPointsCalculator(
            name = "lscc grouped",
            positionToPoints = mapOf(
                    1 to 9,
                    2 to 6,
                    3 to 4,
                    4 to 3,
                    5 to 2
            ),
            defaultPoints = 1
    ) }

    val lsccOverallCalculator by lazy { EventPointsCalculator(
            name = "lscc overall",
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
    ) }
}