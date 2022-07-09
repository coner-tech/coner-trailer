package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext
import tech.coner.trailer.Participant

class IndividualEventResultsCalculator(
    private val comprehensiveEventResultsCalculator: ComprehensiveEventResultsCalculator
) : EventResultsCalculator<IndividualEventResults> {

    override fun calculate(eventContext: EventContext): IndividualEventResults {
        val comprehensiveEventResults = comprehensiveEventResultsCalculator.calculate(eventContext)
        val overallEventResults = comprehensiveEventResults.overallEventResults
        val groupEventResults = comprehensiveEventResults.clazzEventResults
        val participants = overallEventResults.first().participantResults.map { it.participant }
        val allEventResults = mutableListOf<EventResults>().apply {
            addAll(overallEventResults)
            addAll(groupEventResults)
        }
        return IndividualEventResults(
            type = StandardEventResultsTypes.individual,
            runCount = overallEventResults.first().runCount,
            allByParticipant = participants
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
                .toSortedMap(compareBy(Participant::lastName, Participant::firstName, { it.signage?.classingNumber })),
            innerEventResultsTypes = allEventResults.map { it.type }
        )
    }
}