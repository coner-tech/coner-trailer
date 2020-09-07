package org.coner.trailer.seasonpoints

object TestRankingSorts {

    val lscc = RankingSort(
            name = "LSCC",
            steps = listOf(
                    RankingSort.Step.ScoreDescending(0),
                    RankingSort.Step.PositionFinishCountDescending(1),
                    RankingSort.Step.PositionFinishCountDescending(2),
                    RankingSort.Step.PositionFinishCountDescending(3)
            )
    )

    val olscc = RankingSort(
            name = "OLSCC",
            steps = listOf(
                    RankingSort.Step.ScoreDescending(0),
                    RankingSort.Step.AverageMarginOfVictoryDescending(1)
            )
    )
}