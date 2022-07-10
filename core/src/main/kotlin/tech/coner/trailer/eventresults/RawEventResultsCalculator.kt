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
)
