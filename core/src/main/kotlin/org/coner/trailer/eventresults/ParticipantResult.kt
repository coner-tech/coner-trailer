package org.coner.trailer.eventresults

import org.coner.trailer.Participant
import org.coner.trailer.Policy
import org.coner.trailer.Time

data class ParticipantResult(
    val position: Int,
    val score: Score,
    val participant: Participant,
    val diffFirst: Time?,
    val diffPrevious: Time?,
    val scoredRuns: List<ResultRun>
) {

    init {
        require(position >= 1) { "Position is 1-indexed, argument must be greater than or equal to 1" }
    }

    val personalBestRun: ResultRun? by lazy {
        when {
            scoredRuns.isNotEmpty() -> scoredRuns.single { it.personalBest }
            else -> null
        }
    }

    class ScoredRunsComparator(
        private val model: Model
    ) : Comparator<ParticipantResult> {

        fun scoresForSort(participantResult: ParticipantResult): List<Score> {
            val scores = participantResult.scoredRuns
                .take(model.runCount)
                .sortedBy(ResultRun::score)
                .map { it.score }
                .toMutableList()
            while (scores.size < model.runCount) {
                scores += model.policy.participantResultScoredRunsPad
            }
            return scores
        }

        override fun compare(o1: ParticipantResult, o2: ParticipantResult): Int {
            val o1ScoresForSort = scoresForSort(o1)
            val o2ScoresForSort = scoresForSort(o2)
            for ((o1Score, o2Score) in o1ScoresForSort.zip(o2ScoresForSort)) {
                val comparison = o1Score.compareTo(o2Score)
                when {
                    comparison > 0 -> return 1
                    comparison < 0 -> return -1
                    comparison == 0 -> continue
                }
            }
            return 0
        }

        class Model(
            val policy: Policy,
            val runCount: Int
        )
    }
}