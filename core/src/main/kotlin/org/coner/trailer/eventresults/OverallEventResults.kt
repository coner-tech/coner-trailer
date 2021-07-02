package org.coner.trailer.eventresults

class OverallEventResults(
        type: EventResultsType,
        runCount: Int,
        val participantResults: List<ParticipantResult>
) : EventResults(type = type, runCount = runCount)