package org.coner.trailer.eventresults

class OverallResultsReport(
        type: ResultsType,
        runCount: Int,
        val participantResults: List<ParticipantResult>
) : ResultsReport(type = type, runCount = runCount)