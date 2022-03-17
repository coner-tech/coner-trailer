package tech.coner.trailer

import tech.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration
import java.util.*

data class Season(
        val id: UUID = UUID.randomUUID(),
        val name: String,
        val seasonEvents: List<SeasonEvent>,
        val seasonPointsCalculatorConfiguration: SeasonPointsCalculatorConfiguration,
        val takeScoreCountForPoints: Int?
)