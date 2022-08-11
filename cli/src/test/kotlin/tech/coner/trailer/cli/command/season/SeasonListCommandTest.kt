package tech.coner.trailer.cli.command.season

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
import tech.coner.trailer.TestSeasons
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.cli.view.SeasonTableView
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.SeasonService

@ExtendWith(MockKExtension::class)
class SeasonListCommandTest : DIAware {

    lateinit var command: SeasonListCommand

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkDatabaseModule())
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val service: SeasonService by instance()
    @MockK lateinit var view: SeasonTableView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = SeasonListCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should list seasons`() {
        val list = listOf(
                TestSeasons.lscc2019
        )
        every { service.list() } returns list
        val viewRendered = "view rendered"
        every { view.render(list) } returns viewRendered

        command.parse(emptyArray())

        verifySequence {
            service.list()
            view.render(list)
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
    }

}