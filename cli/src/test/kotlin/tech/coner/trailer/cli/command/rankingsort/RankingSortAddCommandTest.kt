package tech.coner.trailer.cli.command.rankingsort

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.RankingSortView
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.constraint.RankingSortPersistConstraints
import tech.coner.trailer.io.service.RankingSortService
import tech.coner.trailer.seasonpoints.RankingSort
import tech.coner.trailer.seasonpoints.TestRankingSorts

@ExtendWith(MockKExtension::class)
class RankingSortAddCommandTest : DIAware {

    lateinit var command: RankingSortAddCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val constraints: RankingSortPersistConstraints by instance()
    private val service: RankingSortService by instance()
    @MockK lateinit var view: RankingSortView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = RankingSortAddCommand(di, global)
            .context { console = testConsole }
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
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }
}
