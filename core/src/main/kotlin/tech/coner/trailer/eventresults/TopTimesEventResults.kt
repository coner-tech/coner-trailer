package tech.coner.trailer.eventresults

import tech.coner.trailer.Class
import tech.coner.trailer.EventContext
import java.util.*

class TopTimesEventResults(
    override val eventContext: EventContext,
    override val runCount: Int,
    val topTimes: SortedMap<Class.Parent, ParticipantResult>
) : EventResults {

    override val type = StandardEventResultsTypes.topTimes
}
