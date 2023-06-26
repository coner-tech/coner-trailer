package tech.coner.trailer.cli.command.rankingsort

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseDataSessionCommandTest
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.RankingSortView
import tech.coner.trailer.io.constraint.RankingSortPersistConstraints
import tech.coner.trailer.io.service.RankingSortService
import tech.coner.trailer.seasonpoints.RankingSort
import tech.coner.trailer.seasonpoints.TestRankingSorts

class RankingSortAddCommandTest : BaseDataSessionCommandTest<RankingSortAddCommand>() {

    private val constraints: RankingSortPersistConstraints by instance()
    private val service: RankingSortService by instance()
    private val view: RankingSortView by instance()

    override fun createCommand(di: DI, global: GlobalModel) = RankingSortAddCommand(di, global)

    @Test
    fun `It should add a ranking sort`() {
        val lsccRankingSort = TestRankingSorts.lscc
        val rankingSort = RankingSort(
                id = lsccRankingSort.id,
                name = lsccRankingSort.name,
                steps = listOf(RankingSort.Step.ScoreDescending())
        )
        every { constraints.hasUniqueName(rankingSort.id, rankingSort.name) } returns true
        every { service.create(eq(rankingSort)) } answers { Unit }
        val viewRendered = "view rendered ${rankingSort.name}"
        every { view.render(rankingSort) } returns viewRendered

        command.parse(arrayOf(
                "--id", rankingSort.id.toString(),
                "--name", rankingSort.name,
                "--score-descending"
        ))

        verifySequence {
            constraints.hasUniqueName(rankingSort.id, rankingSort.name)
            service.create(eq(rankingSort))
            view.render(eq(rankingSort))
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }
}
