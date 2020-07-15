package org.coner.trailer

import org.coner.trailer.eventresults.StandardResultsTypes
import org.coner.trailer.seasonpoints.CalculatorConfigurationModel
import org.coner.trailer.TestParticipantResultPointsCalculators.lsccGroupingCalculator
import org.coner.trailer.TestParticipantResultPointsCalculators.lsccOverallCalculator
import org.coner.trailer.TestParticipantResultPointsCalculators.olsccCalculator

object TestSeasons {

    val lscc2019 = Season(
            name = "LSCC 2019",
            seasonPointsCalculatorConfigurationModel = CalculatorConfigurationModel(mapOf(
                    StandardResultsTypes.competitionGrouped to lsccGroupingCalculator,
                    StandardResultsTypes.overallRawTime to lsccOverallCalculator,
                    StandardResultsTypes.overallHandicapTime to lsccOverallCalculator
            )),
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

    val olscc2019: Season
        get() = Season(
                name = "OLSCC 2019",
                seasonPointsCalculatorConfigurationModel = CalculatorConfigurationModel(mapOf(
                        StandardResultsTypes.competitionGrouped to olsccCalculator,
                        StandardResultsTypes.overallRawTime to olsccCalculator,
                        StandardResultsTypes.overallHandicapTime to olsccCalculator
                )),
                events = listOf(

                )
        )


}