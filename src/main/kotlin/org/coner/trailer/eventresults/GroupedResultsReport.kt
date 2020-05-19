package org.coner.trailer.eventresults

import org.coner.trailer.Grouping

class GroupedResultsReport(
        val groupingsToResultsMap: Map<Grouping, List<ParticipantResult>>
)