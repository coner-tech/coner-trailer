package org.coner.trailer

import org.coner.trailer.seasonpoints.CalculatorConfigurationModel
import java.util.*

class Season(
        val id: UUID = UUID.randomUUID(),
        val name: String,
        val seasonPointsCalculatorConfigurationModel: CalculatorConfigurationModel? = null
) {
}