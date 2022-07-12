package tech.coner.trailer.eventresults

import tech.coner.trailer.Class
import tech.coner.trailer.EventContext

class TopTimesEventResults(
    override val eventContext: EventContext,
    override val type: EventResultsType = StandardEventResultsTypes.topTimes,
    override val runCount: Int,
    val topTimes: Map<Class.Parent, ParticipantResult>
) : EventResults
