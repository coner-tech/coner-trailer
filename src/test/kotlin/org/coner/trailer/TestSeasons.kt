package org.coner.trailer

import org.coner.trailer.eventresults.StandardResultsTypes
import org.coner.trailer.seasonpoints.CalculatorConfigurationModel
import org.coner.trailer.seasonpoints.TestParticipantResultPositionMappedPointsCalculators.LSCC_GROUPING_CALCULATOR
import org.coner.trailer.seasonpoints.TestParticipantResultPositionMappedPointsCalculators.LSCC_OVERALL_CALCULATOR

object TestSeasons {

    val LSCC_2019 = Season(
            name = "LSCC 2019",
            seasonPointsCalculatorConfigurationModel = CalculatorConfigurationModel(mapOf(
                    StandardResultsTypes.competitionGrouped to LSCC_GROUPING_CALCULATOR,
                    StandardResultsTypes.overallRawTime to LSCC_OVERALL_CALCULATOR,
                    StandardResultsTypes.overallHandicapTime to LSCC_OVERALL_CALCULATOR
            )),
            events = listOf(
                    TestSeasonEvents.LSCC_2019.POINTS1,
                    TestSeasonEvents.LSCC_2019.POINTS2,
                    TestSeasonEvents.LSCC_2019.POINTS3,
                    TestSeasonEvents.LSCC_2019.POINTS4,
                    TestSeasonEvents.LSCC_2019.POINTS5,
                    TestSeasonEvents.LSCC_2019.POINTS6,
                    TestSeasonEvents.LSCC_2019.POINTS7,
                    TestSeasonEvents.LSCC_2019.POINTS8,
                    TestSeasonEvents.LSCC_2019.POINTS9
            )
    )
}