package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext

data class OverallEventResults(
        override val eventContext: EventContext,
        override val type: EventResultsType,
        override val runCount: Int,
        val participantResults: List<ParticipantResult>
) : EventResults