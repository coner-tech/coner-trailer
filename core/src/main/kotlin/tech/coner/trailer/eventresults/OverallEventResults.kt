package tech.coner.trailer.eventresults

data class OverallEventResults(
        override val type: EventResultsType,
        override val runCount: Int,
        val participantResults: List<ParticipantResult>
) : EventResults