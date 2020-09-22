package org.coner.trailer.seasonpoints

import org.coner.trailer.Time
import org.coner.trailer.average
import org.coner.trailer.eventresults.Score
import java.util.*

data class RankingSort(
        val id: UUID = UUID.randomUUID(),
        val name: String,
        val steps: List<Step>
) {

    init {
        require(steps.isNotEmpty()) { "Steps required" }
    }

    val comparator: Comparator<PersonStandingAccumulator> by lazy {
        var builder: Comparator<PersonStandingAccumulator>? = null
        steps.forEach { step ->
            builder = when (step) {
                is Step.ScoreDescending -> builder?.thenByDescending(step.comparable)
                        ?: compareByDescending(step.comparable)
                is Step.PositionFinishCountDescending -> builder?.thenByDescending(step.comparable)
                        ?: compareByDescending(step.comparable)
                is Step.AverageMarginOfVictoryDescending -> builder?.thenByDescending(step.comparable)
                        ?: compareByDescending(step.comparable)
            }
        }
        checkNotNull(builder) { "model did not include sort steps" }
    }

    sealed class Step {
        abstract val comparable: (PersonStandingAccumulator) -> Comparable<*>

        data class ScoreDescending(val index: Int? = null) : Step() {
            override val comparable: (PersonStandingAccumulator) -> Comparable<*>
                get() = { it.score }

        }
        data class PositionFinishCountDescending(val position: Int) : Step() {
            override val comparable: (PersonStandingAccumulator) -> Comparable<*>
                get() = { it.positionToFinishCount[position] ?: 0 }

        }
        data class AverageMarginOfVictoryDescending(val index: Int? = null) : Step() {
            override val comparable: (PersonStandingAccumulator) -> Comparable<*>
                get() = { it.marginsOfVictory.average() ?: Time(Score.withoutTime().value) }
        }
    }

}
