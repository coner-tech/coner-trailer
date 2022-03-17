package tech.coner.trailer

import tech.coner.trailer.seasonpoints.SeasonPointsCalculatorConfiguration
import java.util.*

data class SeasonEvent(
        val id: UUID = UUID.randomUUID(),
        val event: Event,
        val eventNumber: Int?,
        val points: Boolean,
        val seasonPointsCalculatorConfiguration: SeasonPointsCalculatorConfiguration? = null
) : Comparable<SeasonEvent> {
    init {
        if (points) {
            require(eventNumber != null) { "Points events require an event number" }
        }
    }

    override fun compareTo(other: SeasonEvent): Int {
        return if (eventNumber != null && other.eventNumber != null) {
            eventNumber.compareTo(other.eventNumber)
        } else {
            event.date.compareTo(other.event.date)
        }
    }
}