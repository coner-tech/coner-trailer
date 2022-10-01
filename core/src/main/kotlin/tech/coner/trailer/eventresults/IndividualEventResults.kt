package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext
import tech.coner.trailer.Participant

data class IndividualEventResults(
    override val eventContext: EventContext,
    val resultsByIndividual: List<Pair<Participant, Map<EventResultsType, ParticipantResult?>>>,
    val innerEventResultsTypes: List<EventResultsType>
) : EventResults {

    override val type: EventResultsType = StandardEventResultsTypes.individual

    object Comparators {
        val standard = compareBy<Pair<Participant, Map<EventResultsType, ParticipantResult?>>> { it.first.lastName }
            .thenBy { it.first.lastName }
            .thenBy { it.first.signage?.classingNumber }
    }
}
