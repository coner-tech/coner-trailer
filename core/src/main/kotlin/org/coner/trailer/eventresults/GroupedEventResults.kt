package org.coner.trailer.eventresults

import org.coner.trailer.Grouping
import java.util.*

class GroupedEventResults(
        type: EventResultsType,
        runCount: Int,
        val groupingsToResultsMap: SortedMap<Grouping, List<ParticipantResult>>
) : EventResults(type = type, runCount = runCount)