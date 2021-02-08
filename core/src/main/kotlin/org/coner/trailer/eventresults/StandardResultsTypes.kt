package org.coner.trailer.eventresults

object StandardResultsTypes {

    val overallRawTime = ResultsType(
        key = "overall-raw-time",
        title = "Overall Raw-Time Results",
        scoreColumnHeading = "Raw Time"
    )
    val overallHandicapTime = ResultsType(
        key = "overall-handicap-time",
        title = "Overall Handicap-Time Results",
        scoreColumnHeading = "Handicap Time"
    )
    val competitionGrouped = ResultsType(
        key = "competition-grouped",
        title = "Competition Grouped Results",
        scoreColumnHeading = "Time"
    )

    fun fromKey(key: String): ResultsType? {
        return when (key) {
            overallRawTime.key -> overallRawTime
            overallHandicapTime.key -> overallHandicapTime
            competitionGrouped.key -> competitionGrouped
            else -> null
        }
    }
}