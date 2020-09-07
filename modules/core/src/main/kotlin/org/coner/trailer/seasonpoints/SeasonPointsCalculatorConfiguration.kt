package org.coner.trailer.seasonpoints

import org.coner.trailer.eventresults.ResultsType

class SeasonPointsCalculatorConfiguration(
        val resultsTypeToCalculatorMap: Map<ResultsType, ParticipantEventResultPointsCalculator>
) {

}