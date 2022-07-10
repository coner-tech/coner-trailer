package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext

class PaxEventResultsCalculator(
    eventContext: EventContext,
    scoredRunsComparator: ParticipantResult.ScoredRunsComparator,
    runEligibilityQualifier: RunEligibilityQualifier,
    runScoreFactory: PaxTimeRunScoreFactory,
    finalScoreFactory: FinalScoreFactory
) : OverallEventResultsCalculator(
    eventContext = eventContext,
    type = StandardEventResultsTypes.pax,
    scoredRunsComparator = scoredRunsComparator,
    runEligibilityQualifier = runEligibilityQualifier,
    runScoreFactory = runScoreFactory,
    finalScoreFactory = finalScoreFactory
)
