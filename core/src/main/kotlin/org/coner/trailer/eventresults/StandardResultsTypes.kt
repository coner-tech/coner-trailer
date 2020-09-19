package org.coner.trailer.eventresults

object StandardResultsTypes {

    val overallRawTime = ResultsType("overall-raw-time", "Overall Raw-Time Results")
    val overallHandicapTime = ResultsType("overall-handicap-time", "Overall Handicap-Time Results")
    val competitionGrouped = ResultsType("competition-grouped", "Competition Grouped Results")

    fun fromKey(key: String): ResultsType? {
        return when (key) {
            overallRawTime.key -> overallRawTime
            overallHandicapTime.key -> overallHandicapTime
            competitionGrouped.key -> competitionGrouped
            else -> null
        }
    }
}