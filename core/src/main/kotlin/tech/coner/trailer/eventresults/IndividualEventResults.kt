package tech.coner.trailer.eventresults

import tech.coner.trailer.Participant
import java.util.*

class IndividualEventResults(
    type: EventResultsType,
    runCount: Int,
    val allByParticipant: SortedMap<Participant, Map<EventResultsType, ParticipantResult>>,
    val innerEventResultsTypes: List<EventResultsType>
) : EventResults(type = type, runCount = runCount)
