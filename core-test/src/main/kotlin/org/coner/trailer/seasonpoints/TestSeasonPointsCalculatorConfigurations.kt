package org.coner.trailer.seasonpoints

import org.coner.trailer.eventresults.StandardResultsTypes

object TestSeasonPointsCalculatorConfigurations {

    val lscc2019 = SeasonPointsCalculatorConfiguration(
            name = "LSCC 2019",
            resultsTypeToEventPointsCalculator = mapOf(
                    StandardResultsTypes.grouped to TestEventPointsCalculators.lsccGroupingCalculator,
                    StandardResultsTypes.raw to TestEventPointsCalculators.lsccOverallCalculator,
                    StandardResultsTypes.pax to TestEventPointsCalculators.lsccOverallCalculator
            ),
            rankingSort = TestRankingSorts.lscc,
    )

    val lscc2019Simplified = SeasonPointsCalculatorConfiguration(
            name = "LSCC 2019 Simplified",
            resultsTypeToEventPointsCalculator = mapOf(
                    StandardResultsTypes.grouped to TestEventPointsCalculators.lsccGroupingCalculator,
                    StandardResultsTypes.raw to TestEventPointsCalculators.lsccOverallCalculator,
                    StandardResultsTypes.pax to TestEventPointsCalculators.lsccOverallCalculator
            ),
            rankingSort = TestRankingSorts.lscc,
    )

    val olsccV1 = SeasonPointsCalculatorConfiguration(
            name = "OLSCC v1",
            resultsTypeToEventPointsCalculator = mapOf(
                    StandardResultsTypes.grouped to TestEventPointsCalculators.olsccCalculator,
                    StandardResultsTypes.raw to TestEventPointsCalculators.olsccCalculator,
                    StandardResultsTypes.pax to TestEventPointsCalculators.olsccCalculator
            ),
            rankingSort = TestRankingSorts.olscc
    )
}