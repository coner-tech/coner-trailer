package org.coner.trailer.seasonpoints

import org.coner.trailer.Time
import org.coner.trailer.average
import org.coner.trailer.eventresults.Score
import java.util.*
import kotlin.Comparator

class RankingSort(
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
                Step.ScoreDescending -> builder?.thenByDescending(step.comparable)
                        ?: compareByDescending(step.comparable)
                is Step.PositionFinishCountDescending -> builder?.thenByDescending(step.comparable)
                        ?: compareByDescending(step.comparable)
                Step.AverageMarginOfVictoryDescending -> builder?.thenByDescending(step.comparable)
                        ?: compareByDescending(step.comparable)
            }
        }
        checkNotNull(builder) { "model did not include sort steps" }
    }

    sealed class Step {
        abstract val comparable: (PersonStandingAccumulator) -> Comparable<*>

        object ScoreDescending : Step() {
            override val comparable: (PersonStandingAccumulator) -> Comparable<*>
                get() = { it.score }
        }
        class PositionFinishCountDescending(val position: Int) : Step() {
            override val comparable: (PersonStandingAccumulator) -> Comparable<*>
                get() = { it.positionToFinishCount[position] ?: 0 }
        }
        object AverageMarginOfVictoryDescending : Step() {
            override val comparable: (PersonStandingAccumulator) -> Comparable<*>
                get() = { it.marginsOfVictory.average() ?: Time(Score.withoutTime().value) }
        }
    }

}
