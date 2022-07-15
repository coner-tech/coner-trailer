package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext
import tech.coner.trailer.Participant
import java.util.*

data class IndividualEventResults(
    override val eventContext: EventContext,
    val allByParticipant: SortedMap<Participant, Map<EventResultsType, ParticipantResult>>,
    val innerEventResultsTypes: List<EventResultsType>
) : EventResults {

    override val type: EventResultsType = StandardEventResultsTypes.individual
}
