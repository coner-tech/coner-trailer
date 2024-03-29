package tech.coner.trailer.eventresults

import tech.coner.trailer.Class
import tech.coner.trailer.EventContext
import tech.coner.trailer.Participant

class ClazzEventResultsCalculator(
    eventContext: EventContext,
    scoredRunsComparator: ParticipantResult.ScoredRunsComparator,
    runEligibilityQualifier: RunEligibilityQualifier,
    runScoreFactory: ClazzRunScoreFactory,
    finalScoreFactory: FinalScoreFactory,
) : AbstractEventResultsCalculator<ClassEventResults>(
    eventContext = eventContext,
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
        runScoreFactory = ClazzRunScoreFactory(
            rawTimes = RawTimeRunScoreFactory(StandardPenaltyFactory(eventContext.event.policy)),
            paxTimes = PaxTimeRunScoreFactory(StandardPenaltyFactory(eventContext.event.policy))
        ),
        finalScoreFactory = finalScoreFactory
    )

    override fun calculate(): ClassEventResults {
        return ClassEventResults(
            eventContext = eventContext,
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