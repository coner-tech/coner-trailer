package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext

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
            allByParticipant = eventContext.participants // TODO: don't expect a result for every participant. not everyone might have taken runs (yet)
                .associateWith { participant ->
                    allEventResults.associate { eventResults ->
                        eventResults.type to when (eventResults) {
                            is OverallEventResults -> eventResults.participantResults.single { it.participant == participant }
                            is ClazzEventResults -> {
                                val groupParticipantResults = eventResults.groupParticipantResults.values
                                    .single { participantResults ->
                                        participantResults.any { it.participant == participant }
                                    }
                                groupParticipantResults.single { it.participant == participant }
                            }
                            else -> throw IllegalArgumentException("Unable to handle event results type: ${eventResults.type.key}")
                        }
                    }
                }
                .toSortedMap(IndividualEventResults.allByParticipantComparator),
            innerEventResultsTypes = allEventResults.map { it.type }
        )
    }
}