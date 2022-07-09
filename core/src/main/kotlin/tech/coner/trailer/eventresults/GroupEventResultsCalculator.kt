package tech.coner.trailer.eventresults

import tech.coner.trailer.Class
import tech.coner.trailer.EventContext
import tech.coner.trailer.Participant

class GroupEventResultsCalculator(
    private val type: EventResultsType,
    scoredRunsComparatorFactory: (EventContext) -> ParticipantResult.ScoredRunsComparator,
    runEligibilityQualifier: RunEligibilityQualifier,
    runScoreFactory: GroupedRunScoreFactory,
    finalScoreFactory: FinalScoreFactory,
) : AbstractEventResultsCalculator<GroupEventResults>(
    scoredRunsComparatorFactory = scoredRunsComparatorFactory,
    runEligibilityQualifier = runEligibilityQualifier,
    runScoreFactory = runScoreFactory,
    finalScoreFactory = finalScoreFactory
) {

    init {
        require(type.clazz == GroupEventResults::class) {
            "Invalid configuration: type must be for class ${GroupEventResults::class}"
        }
    }

    override fun calculate(eventContext: EventContext): GroupEventResults {
        return GroupEventResults(
            type = type,
            runCount = eventContext.extendedParameters.runsPerParticipant,
            groupParticipantResults = eventContext.buildParticipantResultsUnranked()
                .groupBy { it.participant.resultGroup }
                .mapNotNull { (grouping, results) -> grouping?.let { groupingNotNull: Class ->
                    groupingNotNull to results.toRanked()
                } }
                .toMap()
                .toSortedMap()
        )
    }

    private val Participant.resultGroup: Class?
        get() = signage?.classing?.let { it.group ?: it.handicap }
}