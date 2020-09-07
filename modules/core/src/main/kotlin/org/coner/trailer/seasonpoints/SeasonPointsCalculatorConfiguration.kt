package org.coner.trailer.seasonpoints

import org.coner.trailer.eventresults.ResultsType
import java.util.*

data class SeasonPointsCalculatorConfiguration(
        val id: UUID = UUID.randomUUID(),
        val name: String,
        val resultsTypeToCalculatorMap: Map<ResultsType, ParticipantEventResultPointsCalculator>
)