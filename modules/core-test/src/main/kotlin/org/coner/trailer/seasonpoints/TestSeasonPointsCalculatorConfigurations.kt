package org.coner.trailer.seasonpoints

import org.coner.trailer.TestParticipantEventResultPointsCalculators
import org.coner.trailer.eventresults.StandardResultsTypes

object TestSeasonPointsCalculatorConfigurations {

    val lscc2019 = SeasonPointsCalculatorConfiguration(
            name = "LSCC 2019",
            resultsTypeToCalculatorMap = mapOf(
                    StandardResultsTypes.competitionGrouped to TestParticipantEventResultPointsCalculators.lsccGroupingCalculator,
                    StandardResultsTypes.overallRawTime to TestParticipantEventResultPointsCalculators.lsccOverallCalculator,
                    StandardResultsTypes.overallHandicapTime to TestParticipantEventResultPointsCalculators.lsccOverallCalculator
            ),
            rankingSort = TestRankingSorts.lscc,
            takeTopEventScores = 7
    )

    val lscc2019Simplified = SeasonPointsCalculatorConfiguration(
            name = "LSCC 2019 Simplified",
            resultsTypeToCalculatorMap = mapOf(
                    StandardResultsTypes.competitionGrouped to TestParticipantEventResultPointsCalculators.lsccGroupingCalculator,
                    StandardResultsTypes.overallRawTime to TestParticipantEventResultPointsCalculators.lsccOverallCalculator,
                    StandardResultsTypes.overallHandicapTime to TestParticipantEventResultPointsCalculators.lsccOverallCalculator
            ),
            rankingSort = TestRankingSorts.lscc,
            takeTopEventScores = 2
    )

    val olsccV1 = SeasonPointsCalculatorConfiguration(
            name = "OLSCC v1",
            resultsTypeToCalculatorMap = mapOf(
                    StandardResultsTypes.competitionGrouped to TestParticipantEventResultPointsCalculators.olsccCalculator,
                    StandardResultsTypes.overallRawTime to TestParticipantEventResultPointsCalculators.olsccCalculator,
                    StandardResultsTypes.overallHandicapTime to TestParticipantEventResultPointsCalculators.olsccCalculator
            ),
            rankingSort = TestRankingSorts.olscc,
            takeTopEventScores = null // unknown
    )
}