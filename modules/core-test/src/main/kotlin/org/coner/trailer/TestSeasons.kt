package org.coner.trailer

import org.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations

object TestSeasons {

    val lscc2019 = Season(
            name = "LSCC 2019",
            seasonPointsCalculatorConfiguration = TestSeasonPointsCalculatorConfigurations.lscc2019,
            events = listOf(
                    TestSeasonEvents.Lscc2019.points1,
                    TestSeasonEvents.Lscc2019.points2,
                    TestSeasonEvents.Lscc2019.points3,
                    TestSeasonEvents.Lscc2019.points4,
                    TestSeasonEvents.Lscc2019.points5,
                    TestSeasonEvents.Lscc2019.points6,
                    TestSeasonEvents.Lscc2019.points7,
                    TestSeasonEvents.Lscc2019.points8,
                    TestSeasonEvents.Lscc2019.points9
            )
    )

    val lscc2019TieBreaking by lazy { Season(
            name = "LSCC 2019 Tie-Breaking",
            seasonPointsCalculatorConfiguration = TestSeasonPointsCalculatorConfigurations.lscc2019,
            events = listOf(
                    TestSeasonEvents.LsccTieBreaking.points1,
                    TestSeasonEvents.LsccTieBreaking.points2
            )
    ) }

    val lscc2019Simplified by lazy { Season(
            name = "LSCC 2019 Simplified",
            seasonPointsCalculatorConfiguration = TestSeasonPointsCalculatorConfigurations.lscc2019,
            events = listOf(
                    TestSeasonEvents.Lscc2019Simplified.points1,
                    TestSeasonEvents.Lscc2019Simplified.points2,
                    TestSeasonEvents.Lscc2019Simplified.points3
            )
    ) }

    val olscc2019: Season
        get() = Season(
                name = "OLSCC 2019",
                seasonPointsCalculatorConfiguration = TestSeasonPointsCalculatorConfigurations.olsccV1,
                events = listOf(

                )
        )


}