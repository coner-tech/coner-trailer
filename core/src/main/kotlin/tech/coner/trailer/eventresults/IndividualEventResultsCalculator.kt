package tech.coner.trailer.eventresults

import tech.coner.trailer.Event
import tech.coner.trailer.EventContext
import tech.coner.trailer.Participant
import tech.coner.trailer.Signage

class IndividualEventResultsCalculator(
    private val eventContext: EventContext,
    private val overallEventResultsCalculators: List<OverallEventResultsCalculator>,
    private val clazzEventResultsCalculator: ClazzEventResultsCalculator,
) : EventResultsCalculator<IndividualEventResults> {

    override fun calculate(): IndividualEventResults {
        val allEventResults = mutableListOf<EventResults>().apply {
            overallEventResultsCalculators.forEach {
                add(it.calculate())
            }
            add(clazzEventResultsCalculator.calculate())
        }
        return IndividualEventResults(
            eventContext = eventContext,
            resultsByIndividual = eventContext.participants
                .associateWith { participant ->
                    allEventResults.associate { eventResults ->
                        eventResults.type to when (eventResults) {
                            is OverallEventResults -> eventResults.participantResults.singleOrNull { it.participant == participant }
                            is ClassEventResults -> {
                                eventResults.groupParticipantResults.values
                                    .singleOrNull { participantResults ->
                                        participantResults.any { it.participant == participant }
                                    }
                                    ?.singleOrNull { it.participant == participant }
                            }
                            else -> throw IllegalArgumentException("Unable to handle event results type: ${eventResults.type.key}")
                        }
                    }
                }
                .let { map ->
                    // exclude participants without runs from POST and later lifecycle events
                    if (eventContext.event.lifecycle >= Event.Lifecycle.POST) {
                        map.filterValues { value -> !value.containsValue(null) }
                    } else {
                        map
                    }
                }
                .map { it.key to it.value }
                .sortedWith(comparator),
            innerEventResultsTypes = allEventResults.map { it.type }
        )
    }

    private val comparator = compareBy<Pair<Participant, Map<EventResultsType, ParticipantResult?>>> { it.first.lastName }
    .thenBy { it.first.firstName }
    .thenBy { it.first.signage?.sortable() }

    private fun Signage.sortable(): String {
        return "${classing?.group?.abbreviation} ${classing?.handicap?.abbreviation} $number".trim()
    }
}