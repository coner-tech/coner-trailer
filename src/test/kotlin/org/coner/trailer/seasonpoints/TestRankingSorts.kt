package org.coner.trailer.seasonpoints

object TestRankingSorts {
    val LSCC = compareByDescending<PersonStandingAccumulator> { it.score }
            .thenByPositionFinishCountDescending(1)
            .thenByPositionFinishCountDescending(2)
            .thenByPositionFinishCountDescending(3)
}