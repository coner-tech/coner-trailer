package tech.coner.trailer.cli.command.season

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import tech.coner.trailer.TestSeasons
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.cli.view.SeasonView
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import tech.coner.trailer.io.service.SeasonService
import tech.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations

@ExtendWith(MockKExtension::class)
class SeasonAddCommandTest : DIAware {

    lateinit var command: SeasonAddCommand

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkDatabaseModule())
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val service: SeasonService by instance()
    private val spccService: SeasonPointsCalculatorConfigurationService by instance()
    @MockK lateinit var view: SeasonView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = SeasonAddCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should add a season`() {
        val add = TestSeasons.lscc2019.copy(
                seasonEvents = emptyList() // https://github.com/caeos/coner-trailer/issues/27
        )
        val spcc = TestSeasonPointsCalculatorConfigurations.lscc2019
        every { spccService.findByName(add.seasonPointsCalculatorConfiguration.name) } returns spcc
        justRun { service.create(add) }
        val viewRendered = "view rendered"
        every { view.render(add) } returns viewRendered

        command.parse(arrayOf(
                "--id", "${add.id}",
                "--name", add.name,
                "--season-points-calculator-named", add.seasonPointsCalculatorConfiguration.name,
                "--take-score-count-for-points", "${add.takeScoreCountForPoints}"
        ))

        verifySequence {
            spccService.findByName(add.seasonPointsCalculatorConfiguration.name)
            service.create(add)
            view.render(add)
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
    }
}