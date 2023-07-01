package tech.coner.trailer.eventresults

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
        clazz = ClassEventResults::class
    )
    val topTimes = EventResultsType(
        key = "toptimes",
        title = "Top Times",
        titleShort = "Top Times",
        positionColumnHeading = "N/A",
        scoreColumnHeading = "PAX Time",
        clazz = TopTimesEventResults::class
    )
    val comprehensive = EventResultsType(
        key = "comprehensive",
        title = "Comprehensive Results",
        titleShort = "Comprehensive",
        positionColumnHeading = "N/A",
        scoreColumnHeading = "N/A",
        clazz = ComprehensiveEventResults::class
    )
    val individual = EventResultsType(
        key = "individual",
        title = "Individual Results",
        titleShort = "Individual",
        positionColumnHeading = "N/A",
        scoreColumnHeading = "N/A",
        clazz = IndividualEventResults::class
    )

    val all = listOf(raw, pax, clazz, topTimes, comprehensive, individual)
    val allForIndividual = listOf(raw, pax, clazz, comprehensive)
    private val allByKey = all.associateBy { it.key }

    fun fromKey(key: String): EventResultsType? {
        return allByKey[key]
    }
}