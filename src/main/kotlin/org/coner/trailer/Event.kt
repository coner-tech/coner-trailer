package org.coner.trailer

import org.coner.trailer.seasonpoints.CalculatorConfigurationModel
import java.time.LocalDate
import java.util.*

class Event(
        val id: UUID = UUID.randomUUID(),
        val date: LocalDate,
        val name: String,
        val seasonPointsCalculatorConfigurationModel: CalculatorConfigurationModel? = null
) {
}