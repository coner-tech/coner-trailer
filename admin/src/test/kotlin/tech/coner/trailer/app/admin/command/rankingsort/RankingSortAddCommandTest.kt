package tech.coner.trailer.app.admin.command.rankingsort

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.app.admin.view.RankingSortView
import tech.coner.trailer.io.constraint.RankingSortPersistConstraints
import tech.coner.trailer.io.service.RankingSortService
import tech.coner.trailer.seasonpoints.RankingSort
import tech.coner.trailer.seasonpoints.TestRankingSorts

class RankingSortAddCommandTest : BaseDataSessionCommandTest<RankingSortAddCommand>() {

    private val constraints: RankingSortPersistConstraints by instance()
    private val service: RankingSortService by instance()
    private val view: RankingSortView by instance()

    override fun DirectDI.createCommand() = instance<RankingSortAddCommand>()

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

        val testResult = command.test(arrayOf(
                "--id", rankingSort.id.toString(),
                "--name", rankingSort.name,
                "--score-descending"
        ))

        verifySequence {
            constraints.hasUniqueName(rankingSort.id, rankingSort.name)
            service.create(eq(rankingSort))
            view.render(eq(rankingSort))
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
    }
}
