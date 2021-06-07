package org.coner.trailer.eventresults

import org.coner.trailer.Grouping
import java.util.*

class GroupedResultsReport(
        type: ResultsType,
        runCount: Int,
        val groupingsToResultsMap: SortedMap<Grouping, List<ParticipantResult>>
) : ResultsReport(type = type, runCount = runCount)