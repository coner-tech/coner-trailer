package org.coner.trailer.eventresults

class OverallResultsReport(
        type: ResultsType,
        val participantResults: List<ParticipantResult>
) : ResultsReport(type = type)