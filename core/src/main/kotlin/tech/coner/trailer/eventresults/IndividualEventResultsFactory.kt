package tech.coner.trailer.eventresults

import tech.coner.trailer.Participant

class IndividualEventResultsFactory {

    fun factory(
        comprehensiveEventResults: ComprehensiveEventResults
    ): IndividualEventResults {
        val overallEventResults = comprehensiveEventResults.overallEventResults
        val groupEventResults = comprehensiveEventResults.groupEventResults
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
                            is GroupEventResults -> {
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