package org.coner.trailer.eventresults

import org.coner.trailer.Grouping

class GroupedResultsReport(
        type: ResultsType,
        val groupingsToResultsMap: Map<Grouping, List<ParticipantResult>>
) : ResultsReport(type = type)