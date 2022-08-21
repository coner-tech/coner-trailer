package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext

class RawEventResultsCalculator(
    eventContext: EventContext,
    scoredRunsComparatorFactory: ParticipantResult.ScoredRunsComparator,
    runEligibilityQualifier: RunEligibilityQualifier,
    runScoreFactory: RawTimeRunScoreFactory,
    finalScoreFactory: FinalScoreFactory
) : OverallEventResultsCalculator(
    eventContext = eventContext,
    type = StandardEventResultsTypes.raw,
    scoredRunsComparator = scoredRunsComparatorFactory,
    runEligibilityQualifier = runEligibilityQualifier,
    runScoreFactory = runScoreFactory,
    finalScoreFactory = finalScoreFactory
) {
    companion object {
        fun create(
            eventContext: EventContext,
            scoredRunsComparatorFactory: ParticipantResult.ScoredRunsComparator = ParticipantResult.ScoredRunsComparator(eventContext.extendedParameters.runsPerParticipant),
            runEligibilityQualifier: RunEligibilityQualifier = RunEligibilityQualifier(),
            runScoreFactory: RawTimeRunScoreFactory = RawTimeRunScoreFactory(StandardPenaltyFactory(eventContext.event.policy)),
            finalScoreFactory: FinalScoreFactory
        ) = RawEventResultsCalculator(
            eventContext = eventContext,
            scoredRunsComparatorFactory = scoredRunsComparatorFactory,
            runEligibilityQualifier = runEligibilityQualifier,
            runScoreFactory = runScoreFactory,
            finalScoreFactory = finalScoreFactory
        )
    }
}
