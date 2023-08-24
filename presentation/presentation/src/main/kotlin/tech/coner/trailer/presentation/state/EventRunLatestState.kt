package tech.coner.trailer.presentation.state

import tech.coner.trailer.EventContext
import tech.coner.trailer.Run

data class EventRunLatestState(
    val eventContext: EventContext? = null,
    val count: Int = 5
) {
    val latestRuns: List<Run>?
        get() = eventContext?.runs?.takeLast(count)
}
