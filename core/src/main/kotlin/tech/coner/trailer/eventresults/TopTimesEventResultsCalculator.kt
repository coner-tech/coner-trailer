package tech.coner.trailer.eventresults

import tech.coner.trailer.Class
import tech.coner.trailer.EventContext

class TopTimesEventResultsCalculator(
    private val eventContext: EventContext,
    private val overallEventResultsCalculator: OverallEventResultsCalculator,
) : EventResultsCalculator<TopTimesEventResults> {

    override fun calculate(): TopTimesEventResults {
        val overallResults = overallEventResultsCalculator.calculate()
        val groupParentTopTimes = overallResults.participantResults
            .groupBy { it.participant.signage?.classing?.group?.parent }
            .mapNotNull { (parent, participantResults) ->
                if (parent == null) {
                    return@mapNotNull null
                }
                val topParticipantResult = participantResults.firstOrNull()
                    ?: return@mapNotNull null
                parent to topParticipantResult
            }
        val handicapParentTopTimes = overallResults.participantResults
            .groupBy { it.participant.signage?.classing?.handicap?.parent }
            .mapNotNull { (parent, participantResults) ->
                if (parent == null) {
                    return@mapNotNull null
                }
                val topParticipantResult = participantResults.firstOrNull()
                    ?: return@mapNotNull null
                parent to topParticipantResult
            }
        return TopTimesEventResults(
            eventContext = eventContext,
            topTimes = mutableListOf<Pair<Class.Parent, ParticipantResult>>()
                .apply {
                    addAll(handicapParentTopTimes)
                    addAll(groupParentTopTimes)
                }
                .sortedBy { (_, participantResult) -> participantResult.score }
                .distinctBy { (parent, _) -> parent }
                .associate { it.copy(second = it.second.copy(position = 1)) }
                .toSortedMap(compareBy(Class.Parent::sort))
        )
    }
}