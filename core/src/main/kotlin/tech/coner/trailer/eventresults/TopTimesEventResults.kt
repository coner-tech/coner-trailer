package tech.coner.trailer.eventresults

import tech.coner.trailer.Class
import tech.coner.trailer.EventContext
import java.util.*

data class TopTimesEventResults(
    override val eventContext: EventContext,
    val topTimes: SortedMap<Class.Parent, ParticipantResult>
) : EventResults {

    override val type = StandardEventResultsTypes.topTimes
}
