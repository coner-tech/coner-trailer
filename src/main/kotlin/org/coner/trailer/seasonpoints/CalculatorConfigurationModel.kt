package org.coner.trailer.seasonpoints

import org.coner.trailer.eventresults.ResultsType

class CalculatorConfigurationModel(
        val resultsTypeToCalculatorMap: Map<ResultsType, ParticipantResultPositionMappedPointsCalculator>
) {

}