package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext

class RawEventResultsCalculator(
    eventContext: EventContext,
    scoredRunsComparator: ParticipantResult.ScoredRunsComparator,
    runEligibilityQualifier: RunEligibilityQualifier,
    runScoreFactory: RawTimeRunScoreFactory,
    finalScoreFactory: FinalScoreFactory
) : OverallEventResultsCalculator(
    eventContext = eventContext,
    type = StandardEventResultsTypes.raw,
    scoredRunsComparator = scoredRunsComparator,
    runEligibilityQualifier = runEligibilityQualifier,
    runScoreFactory = runScoreFactory,
    finalScoreFactory = finalScoreFactory
) {

    constructor(
        eventContext: EventContext,
        finalScoreFactory: FinalScoreFactory
    ) : this(
        eventContext = eventContext,
        scoredRunsComparator = ParticipantResult.ScoredRunsComparator(eventContext.extendedParameters.runsPerParticipant),
        runEligibilityQualifier = RunEligibilityQualifier(),
        runScoreFactory = RawTimeRunScoreFactory(StandardPenaltyFactory(eventContext.event.policy)),
        finalScoreFactory = finalScoreFactory
    )
}
