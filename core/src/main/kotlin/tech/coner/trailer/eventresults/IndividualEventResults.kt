package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext
import tech.coner.trailer.Participant
import java.util.*

data class IndividualEventResults(
    override val eventContext: EventContext,
    override val type: EventResultsType = StandardEventResultsTypes.individual,
    override val runCount: Int,
    val allByParticipant: SortedMap<Participant, Map<EventResultsType, ParticipantResult>>,
    val innerEventResultsTypes: List<EventResultsType>
) : EventResults
