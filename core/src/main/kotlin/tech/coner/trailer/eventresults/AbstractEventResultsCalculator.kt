package tech.coner.trailer.eventresults

import tech.coner.trailer.EventContext
import tech.coner.trailer.Participant
import tech.coner.trailer.Run
import tech.coner.trailer.Time

abstract class AbstractEventResultsCalculator<ER : EventResults>(
    protected val eventContext: EventContext,
    protected val scoredRunsComparator: ParticipantResult.ScoredRunsComparator,
    protected val runEligibilityQualifier: RunEligibilityQualifier,
    protected val runScoreFactory: RunScoreFactory,
    protected val finalScoreFactory: FinalScoreFactory
) : EventResultsCalculator<ER> {

    protected fun EventContext.buildRunsByParticipant(): Map<Participant, List<Run>> {
        return runs
            .filter { it.participant != null }
            .filter { !it.rerun }
            .groupBy { it.participant!! }
    }

    protected fun EventContext.buildParticipantResultsUnranked(): List<ParticipantResult> {
        val runsByParticipant = buildRunsByParticipant()
        return participants
            .mapNotNull { participant: Participant ->
                val participantRuns: List<Run> = runsByParticipant[participant] ?: return@mapNotNull null
                val participantResultRuns: List<ResultRun> = participantRuns
                    .filterIndexed { index: Int, run: Run ->
                        runEligibilityQualifier.check(
                            run = run,
                            participantResultRunIndex = index,
                            maxRunCount = extendedParameters.runsPerParticipant
                        )
                    }
                    .map { ResultRun(run = it, score = runScoreFactory.score(it)) }
                ParticipantResult(
                    score = finalScoreFactory.score(participantResultRuns) ?: return@mapNotNull null,
                    participant = participant,
                    allRuns = participantRuns,
                    scoredRuns = participantResultRuns,
                    personalBestScoredRunIndex = finalScoreFactory.bestRun(participantResultRuns)?.let { participantResultRuns.indexOf(it) },
                    // positions and diffs are calculated after sorting by score
                    position = Int.MAX_VALUE,
                    diffFirst = null,
                    diffPrevious = null
                )
            }
            .sortedWith(compareBy(ParticipantResult::score).then(scoredRunsComparator))
    }

    protected fun List<ParticipantResult>.toRanked(): List<ParticipantResult> {
        return mapIndexed { index: Int, participantResult: ParticipantResult ->
                participantResult.copy(
                    position = index + 1,
                    diffFirst = when {
                        index == 0 -> null
                        participantResult.score.penalty?.diff == false -> null
                        else -> Time(participantResult.score.value - this[0].score.value)
                    },
                    diffPrevious = when {
                        index == 0 -> null
                        participantResult.score.penalty?.diff == false -> null
                        else -> Time(participantResult.score.value - this[index - 1].score.value)
                    }
                )
            }
    }
}