package tech.coner.trailer.eventresults

import tech.coner.trailer.Class
import tech.coner.trailer.EventContext
import java.util.*

data class ClassEventResults(
        override val eventContext: EventContext,
        val groupParticipantResults: SortedMap<Class, List<ParticipantResult>>,
) : EventResults {

        override val type: EventResultsType = StandardEventResultsTypes.clazz
}
