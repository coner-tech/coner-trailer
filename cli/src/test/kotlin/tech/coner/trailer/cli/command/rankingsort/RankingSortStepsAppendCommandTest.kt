package tech.coner.trailer.cli.command.rankingsort

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.RankingSortView
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.RankingSortService
import tech.coner.trailer.seasonpoints.RankingSort
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*

@ExtendWith(MockKExtension::class)
class RankingSortStepsAppendCommandTest : DIAware {

    lateinit var command: RankingSortStepsAppendCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val service: RankingSortService by instance()
    @MockK lateinit var view: RankingSortView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = RankingSortStepsAppendCommand(di, global)
            .context { console = testConsole }
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
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }
}
