package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext

abstract class OverallEventResultsCalculator(
    private val type: EventResultsType,
    scoredRunsComparatorFactory: (EventContext) -> ParticipantResult.ScoredRunsComparator,
    runEligibilityQualifier: RunEligibilityQualifier,
    runScoreFactory: RunScoreFactory,
    finalScoreFactory: FinalScoreFactory
) : AbstractEventResultsCalculator<OverallEventResults>(
    scoredRunsComparatorFactory = scoredRunsComparatorFactory,
    runEligibilityQualifier = runEligibilityQualifier,
    runScoreFactory = runScoreFactory,
    finalScoreFactory = finalScoreFactory
) {

    init {
        require(type.clazz == OverallEventResults::class) {
            "Invalid configuration: type must be for class ${OverallEventResults::class}"
        }
    }

    override fun calculate(eventContext: EventContext): OverallEventResults {
        val participantResultsUnranked: List<ParticipantResult> = eventContext.buildParticipantResultsUnranked()
        return OverallEventResults(
            type = type,
            runCount = eventContext.extendedParameters.runsPerParticipant,
            participantResults = participantResultsUnranked.toRanked()
        )
    }
}