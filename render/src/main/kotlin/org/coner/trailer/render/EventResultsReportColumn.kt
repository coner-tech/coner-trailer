package org.coner.trailer.render

enum class EventResultsReportColumn {
    POSITION,
    SIGNAGE,
    NAME,
    CAR_MODEL,
    SCORE,
    DIFF_FIRST,
    DIFF_PREVIOUS,
    RUNS
}

val standardEventResultsReportColumns = EventResultsReportColumn
    .values()
    .toList()