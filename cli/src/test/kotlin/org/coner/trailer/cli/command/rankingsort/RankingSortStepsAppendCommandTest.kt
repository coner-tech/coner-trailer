package org.coner.trailer.cli.command.rankingsort

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.RankingSortView
import org.coner.trailer.io.service.RankingSortService
import org.coner.trailer.seasonpoints.RankingSort
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

class RankingSortStepsAppendCommandTest {

    lateinit var command: RankingSortStepsAppendCommand

    @MockK
    lateinit var service: RankingSortService
    @MockK
    lateinit var view: RankingSortView

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        console = StringBufferConsole()
        arrangeCommand()
    }

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

        command.parse(arrayOf(
                before.id.toString(),
                "--position-finish-count-descending", "--position", "1"
        ))

        verifySequence {
            service.findById(before.id)
            service.update(eq(expected))
            view.render(eq(expected))
        }
        assertThat(console.output).isEqualTo(viewRendered)
    }
}

private fun RankingSortStepsAppendCommandTest.arrangeCommand() {
    val di = DI {
        bind<RankingSortService>() with instance(service)
        bind<RankingSortView>() with instance(view)
    }
    command = RankingSortStepsAppendCommand(
            di = di,
            useConsole = console
    )
}