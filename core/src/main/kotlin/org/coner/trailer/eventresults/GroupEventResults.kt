package org.coner.trailer.eventresults

import org.coner.trailer.Class
import java.util.*

class GroupEventResults(
        type: EventResultsType,
        runCount: Int,
        val groupParticipantResults: SortedMap<Class, List<ParticipantResult>>
) : EventResults(type = type, runCount = runCount)