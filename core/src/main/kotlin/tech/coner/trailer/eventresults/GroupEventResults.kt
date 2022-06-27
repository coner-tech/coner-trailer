package tech.coner.trailer.eventresults

import tech.coner.trailer.Class
import java.util.*

data class GroupEventResults(
        override val type: EventResultsType = StandardEventResultsTypes.clazz,
        override val runCount: Int,
        val groupParticipantResults: SortedMap<Class, List<ParticipantResult>>,
        val parentClassTopTimes: List<ParentClassTopTime>
) : EventResults {
        data class ParentClassTopTime(val parent: Class.Parent, val participantResult: ParticipantResult)
}