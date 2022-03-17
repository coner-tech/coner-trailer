package tech.coner.trailer.eventresults

import tech.coner.trailer.Participant
import tech.coner.trailer.Run
import tech.coner.trailer.Time

data class ParticipantResult(
    val position: Int,
    val score: Score,
    val participant: Participant,
    val diffFirst: Time?,
    val diffPrevious: Time?,
    val allRuns: List<Run>,
    val scoredRuns: List<ResultRun>,
    val personalBestScoredRunIndex: Int?
) {

    init {
        require(position >= 1) { "Position is 1-indexed, argument must be greater than or equal to 1" }
    }

    val personalBestRun: ResultRun?
        get() = personalBestScoredRunIndex?.let { scoredRuns[it] }

    class ScoredRunsComparator(
        private val runCount: Int
    ) : Comparator<ParticipantResult> {

        fun scoresForSort(participantResult: ParticipantResult): List<Score> {
            return participantResult.scoredRuns
                .take(runCount)
                .sortedBy { it.score }
                .map { it.score }
                .toList()
        }

        override fun compare(o1: ParticipantResult, o2: ParticipantResult): Int {
            val o1ScoresForSort = scoresForSort(o1)
            val o2ScoresForSort = scoresForSort(o2)
            for (i in 0 .. maxOf(o1ScoresForSort.lastIndex, o2ScoresForSort.lastIndex)) {
                val o1Candidate = o1ScoresForSort.getOrNull(i)
                val o2Candidate = o2ScoresForSort.getOrNull(i)
                val comparison: Int = when {
                    o1Candidate != null && o2Candidate != null -> o1Candidate.compareTo(o2Candidate)
                    o1Candidate != null && o2Candidate == null -> -1
                    o1Candidate == null && o2Candidate != null -> 1
                    else -> throw IllegalStateException()
                }
                when {
                    comparison > 0 -> return 1
                    comparison < 0 -> return -1
                    comparison == 0 -> continue
                }
            }
            return 0
        }
    }
}