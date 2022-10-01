package tech.coner.trailer.render

enum class EventResultsColumn {
    POSITION,
    SIGNAGE,
    NAME,
    CAR_MODEL,
    DIFF_FIRST,
    DIFF_PREVIOUS,
    SCORE,
}

val standardEventResultsColumns = EventResultsColumn
    .values()
    .toList()