package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext

class RawEventResultsCalculator(
    scoredRunsComparatorFactory: (EventContext) -> ParticipantResult.ScoredRunsComparator,
    runEligibilityQualifier: RunEligibilityQualifier,
    runScoreFactory: RawTimeRunScoreFactory,
    finalScoreFactory: FinalScoreFactory
) : OverallEventResultsCalculator(
    type = StandardEventResultsTypes.raw,
    scoredRunsComparatorFactory = scoredRunsComparatorFactory,
    runEligibilityQualifier = runEligibilityQualifier,
    runScoreFactory = runScoreFactory,
    finalScoreFactory = finalScoreFactory
)
