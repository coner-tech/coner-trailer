package org.coner.trailer.seasonpoints

import org.coner.trailer.eventresults.StandardEventResultsTypes

object TestSeasonPointsCalculatorConfigurations {

    val lscc2019 = SeasonPointsCalculatorConfiguration(
            name = "LSCC 2019",
            eventResultsTypeToEventPointsCalculator = mapOf(
                    StandardEventResultsTypes.grouped to TestEventPointsCalculators.lsccGroupingCalculator,
                    StandardEventResultsTypes.raw to TestEventPointsCalculators.lsccOverallCalculator,
                    StandardEventResultsTypes.pax to TestEventPointsCalculators.lsccOverallCalculator
            ),
            rankingSort = TestRankingSorts.lscc,
    )

    val lscc2019Simplified = SeasonPointsCalculatorConfiguration(
            name = "LSCC 2019 Simplified",
            eventResultsTypeToEventPointsCalculator = mapOf(
                    StandardEventResultsTypes.grouped to TestEventPointsCalculators.lsccGroupingCalculator,
                    StandardEventResultsTypes.raw to TestEventPointsCalculators.lsccOverallCalculator,
                    StandardEventResultsTypes.pax to TestEventPointsCalculators.lsccOverallCalculator
            ),
            rankingSort = TestRankingSorts.lscc,
    )
}