package tech.coner.trailer.eventresults

import tech.coner.trailer.Class
import tech.coner.trailer.EventContext
import java.util.*

data class ClazzEventResults(
        override val eventContext: EventContext,
        override val type: EventResultsType = StandardEventResultsTypes.clazz,
        override val runCount: Int,
        val groupParticipantResults: SortedMap<Class, List<ParticipantResult>>,
) : EventResults
