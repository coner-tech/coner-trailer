package org.coner.trailer.eventresults

object StandardResultsTypes {

    val raw = ResultsType(
        key = "raw",
        title = "Raw Results",
        scoreColumnHeading = "Raw Time"
    )
    val pax = ResultsType(
        key = "pax",
        title = "PAX Results",
        scoreColumnHeading = "PAX Time"
    )
    val grouped = ResultsType(
        key = "class",
        title = "Class Results",
        scoreColumnHeading = "Time"
    )

    val all = listOf(raw, pax, grouped)

    fun fromKey(key: String): ResultsType? {
        return when (key) {
            raw.key -> raw
            pax.key -> pax
            grouped.key -> grouped
            else -> null
        }
    }
}