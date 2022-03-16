package org.coner.trailer.eventresults

import org.coner.trailer.Class
import java.util.*

class GroupEventResults(
        type: EventResultsType,
        runCount: Int,
        val groupParticipantResults: SortedMap<Class, List<ParticipantResult>>,
        val parentClassTopTimes: List<ParentClassTopTime>
) : EventResults(type = type, runCount = runCount) {

        data class ParentClassTopTime(val parent: Class.Parent, val participantResult: ParticipantResult)
}