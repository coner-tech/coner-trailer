package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext

class PaxEventResultsCalculator(
    scoredRunsComparatorFactory: (EventContext) -> ParticipantResult.ScoredRunsComparator,
    runEligibilityQualifier: RunEligibilityQualifier,
    runScoreFactory: PaxTimeRunScoreFactory,
    finalScoreFactory: FinalScoreFactory
) : OverallEventResultsCalculator(
    type = StandardEventResultsTypes.pax,
    scoredRunsComparatorFactory = scoredRunsComparatorFactory,
    runEligibilityQualifier = runEligibilityQualifier,
    runScoreFactory = runScoreFactory,
    finalScoreFactory = finalScoreFactory
)
