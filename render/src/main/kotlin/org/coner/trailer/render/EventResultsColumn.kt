package org.coner.trailer.render

enum class EventResultsColumn {
    POSITION,
    SIGNAGE,
    NAME,
    CAR_MODEL,
    SCORE,
    DIFF_FIRST,
    DIFF_PREVIOUS,
    RUNS
}

val standardEventResultsColumns = EventResultsColumn
    .values()
    .toList()