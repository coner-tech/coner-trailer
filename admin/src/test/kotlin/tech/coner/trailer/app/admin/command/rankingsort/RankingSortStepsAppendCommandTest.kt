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
import tech.coner.trailer.io.service.RankingSortService
import tech.coner.trailer.seasonpoints.RankingSort

class RankingSortStepsAppendCommandTest : BaseDataSessionCommandTest<RankingSortStepsAppendCommand>() {

    private val service: RankingSortService by instance()
    private val view: RankingSortView by instance()

    override fun DirectDI.createCommand() = instance<RankingSortStepsAppendCommand>()

    @Test
    fun `It should append step`() {
        val before = RankingSort(
                name = "ranking sort",
                steps = listOf(RankingSort.Step.ScoreDescending())
        )
        val append = RankingSort.Step.PositionFinishCountDescending(1)
        val expected = before.copy(
                steps = listOf(
                        RankingSort.Step.ScoreDescending(),
                        append
                )
        )
        every { service.findById(before.id) } returns before
        every { service.update(eq(expected)) } answers { Unit }
        val viewRendered = "view rendered ${expected.name}"
        every { view.render(eq(expected)) } returns viewRendered

        val testResult = command.test(arrayOf(
                before.id.toString(),
                "--position-finish-count-descending", "--position", "1"
        ))

        verifySequence {
            service.findById(before.id)
            service.update(eq(expected))
            view.render(eq(expected))
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
    }
}
