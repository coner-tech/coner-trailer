package org.coner.trailer.seasonpoints

object TestRankingSorts {

    val lscc = RankingSort(
            name = "LSCC",
            steps = listOf(
                    RankingSort.Step.ScoreDescending,
                    RankingSort.Step.PositionFinishCountDescending(1),
                    RankingSort.Step.PositionFinishCountDescending(2),
                    RankingSort.Step.PositionFinishCountDescending(3)
            )
    )

    val olscc = RankingSort(
            name = "OLSCC",
            steps = listOf(
                    RankingSort.Step.ScoreDescending,
                    RankingSort.Step.AverageMarginOfVictoryDescending
            )
    )
}