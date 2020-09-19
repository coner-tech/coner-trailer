package org.coner.trailer

import org.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration
import java.util.*

data class Season(
        val id: UUID = UUID.randomUUID(),
        val name: String,
        val events: List<SeasonEvent>,
        val seasonPointsCalculatorConfiguration: SeasonPointsCalculatorConfiguration,
        val takeScoreCountForPoints: Int?
)