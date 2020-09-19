package org.coner.trailer.cli.command.rankingsort

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.RankingSortView
import org.coner.trailer.io.constraint.RankingSortPersistConstraints
import org.coner.trailer.io.service.RankingSortService
import org.coner.trailer.seasonpoints.RankingSort
import org.coner.trailer.seasonpoints.TestRankingSorts
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

class RankingSortAddCommandTest {

    lateinit var command: RankingSortAddCommand

    @MockK
    lateinit var constraints: RankingSortPersistConstraints
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
        assertThat(console.output).isEqualTo(viewRendered)
    }
}

private fun RankingSortAddCommandTest.arrangeCommand() {
    val di = DI {
        bind<RankingSortPersistConstraints>() with instance(constraints)
        bind<RankingSortService>() with instance(service)
        bind<RankingSortView>() with instance(view)
    }
    command = RankingSortAddCommand(
            useConsole = console,
            di = di
    )
}