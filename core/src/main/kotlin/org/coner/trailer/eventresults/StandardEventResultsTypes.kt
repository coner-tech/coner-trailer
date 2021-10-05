package org.coner.trailer.eventresults

object StandardEventResultsTypes {

    val raw = EventResultsType(
        key = "raw",
        title = "Raw Results",
        titleShort = "Raw",
        positionColumnHeading = "Raw Pos.",
        scoreColumnHeading = "Raw Time",
        clazz = OverallEventResults::class
    )
    val pax = EventResultsType(
        key = "pax",
        title = "PAX Results",
        titleShort = "PAX",
        positionColumnHeading = "PAX Pos.",
        scoreColumnHeading = "PAX Time",
        clazz = OverallEventResults::class
    )
    val clazz = EventResultsType(
        key = "class",
        title = "Class Results",
        titleShort = "Class",
        positionColumnHeading = "Class Pos.",
        scoreColumnHeading = "Class Time",
        clazz = GroupEventResults::class
    )
    val individual = EventResultsType(
        key = "individual",
        title = "Individual Results",
        titleShort = "Individual",
        positionColumnHeading = "N/A",
        scoreColumnHeading = "N/A",
        clazz = IndividualEventResults::class
    )

    val all = listOf(raw, pax, clazz, individual)
    val allForIndividual = all.filterNot { it == individual }

    fun fromKey(key: String): EventResultsType? {
        return when (key) {
            raw.key -> raw
            pax.key -> pax
            clazz.key -> clazz
            else -> null
        }
    }
}