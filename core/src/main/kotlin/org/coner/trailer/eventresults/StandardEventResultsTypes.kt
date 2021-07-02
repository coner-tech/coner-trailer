package org.coner.trailer.eventresults

object StandardEventResultsTypes {

    val raw = EventResultsType(
        key = "raw",
        title = "Raw Results",
        scoreColumnHeading = "Raw Time"
    )
    val pax = EventResultsType(
        key = "pax",
        title = "PAX Results",
        scoreColumnHeading = "PAX Time"
    )
    val grouped = EventResultsType(
        key = "class",
        title = "Class Results",
        scoreColumnHeading = "Time"
    )

    val all = listOf(raw, pax, grouped)

    fun fromKey(key: String): EventResultsType? {
        return when (key) {
            raw.key -> raw
            pax.key -> pax
            grouped.key -> grouped
            else -> null
        }
    }
}