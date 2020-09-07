package org.coner.trailer

import org.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration
import java.util.*

class Season(
        val id: UUID = UUID.randomUUID(),
        val name: String,
        val seasonPointsCalculatorConfiguration: SeasonPointsCalculatorConfiguration,
        val events: List<SeasonEvent>
)