package org.coner.trailer

import org.coner.trailer.seasonpoints.CalculatorConfigurationModel
import java.util.*

data class SeasonEvent(
        val id: UUID = UUID.randomUUID(),
        val event: Event,
        val eventNumber: Int?,
        val points: Boolean,
        val seasonPointsCalculatorConfigurationModel: CalculatorConfigurationModel? = null
) {
    init {
        if (points) {
            require(eventNumber != null) { "Points events require an event number" }
        }
    }
}