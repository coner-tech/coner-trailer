package org.coner.trailer.seasonpoints

import org.coner.trailer.eventresults.StandardResultsTypes

object TestSeasonPointsCalculatorConfigurations {

    val lscc2019 = SeasonPointsCalculatorConfiguration(
            name = "LSCC 2019",
            resultsTypeToEventPointsCalculator = mapOf(
                    StandardResultsTypes.competitionGrouped to TestEventPointsCalculators.lsccGroupingCalculator,
                    StandardResultsTypes.overallRawTime to TestEventPointsCalculators.lsccOverallCalculator,
                    StandardResultsTypes.overallHandicapTime to TestEventPointsCalculators.lsccOverallCalculator
            ),
            rankingSort = TestRankingSorts.lscc,
    )

    val lscc2019Simplified = SeasonPointsCalculatorConfiguration(
            name = "LSCC 2019 Simplified",
            resultsTypeToEventPointsCalculator = mapOf(
                    StandardResultsTypes.competitionGrouped to TestEventPointsCalculators.lsccGroupingCalculator,
                    StandardResultsTypes.overallRawTime to TestEventPointsCalculators.lsccOverallCalculator,
                    StandardResultsTypes.overallHandicapTime to TestEventPointsCalculators.lsccOverallCalculator
            ),
            rankingSort = TestRankingSorts.lscc,
    )

    val olsccV1 = SeasonPointsCalculatorConfiguration(
            name = "OLSCC v1",
            resultsTypeToEventPointsCalculator = mapOf(
                    StandardResultsTypes.competitionGrouped to TestEventPointsCalculators.olsccCalculator,
                    StandardResultsTypes.overallRawTime to TestEventPointsCalculators.olsccCalculator,
                    StandardResultsTypes.overallHandicapTime to TestEventPointsCalculators.olsccCalculator
            ),
            rankingSort = TestRankingSorts.olscc
    )
}