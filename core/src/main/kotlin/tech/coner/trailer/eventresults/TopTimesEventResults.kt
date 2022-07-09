package tech.coner.trailer.eventresults

import tech.coner.trailer.Class

class TopTimesEventResults(
    override val type: EventResultsType = StandardEventResultsTypes.topTimes,
    override val runCount: Int,
    val topTimes: Map<Class.Parent, ParticipantResult>
) : EventResults
