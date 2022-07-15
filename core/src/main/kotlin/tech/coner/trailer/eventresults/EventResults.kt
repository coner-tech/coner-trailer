package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext

interface EventResults {
        val eventContext: EventContext
        val type: EventResultsType
        val runCount: Int
                get() = eventContext.extendedParameters.runsPerParticipant
}