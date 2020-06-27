package org.coner.trailer.seasonpoints

object TestRankingSorts {
    val lscc: Comparator<PersonStandingAccumulator>
        get() = compareByDescending<PersonStandingAccumulator> { it.score }
                .thenByPositionFinishCountDescending(1)
                .thenByPositionFinishCountDescending(2)
                .thenByPositionFinishCountDescending(3)

    val olscc: Comparator<PersonStandingAccumulator>
        get() = compareByDescending<PersonStandingAccumulator> { it.score }
                .thenByAverageMarginOfVictoryDescending()
}