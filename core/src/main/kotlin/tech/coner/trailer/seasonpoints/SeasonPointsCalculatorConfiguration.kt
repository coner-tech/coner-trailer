package tech.coner.trailer.seasonpoints

import tech.coner.trailer.eventresults.EventResultsType
import java.util.*

data class SeasonPointsCalculatorConfiguration(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val eventResultsTypeToEventPointsCalculator: Map<EventResultsType, EventPointsCalculator>,
    val rankingSort: RankingSort
)