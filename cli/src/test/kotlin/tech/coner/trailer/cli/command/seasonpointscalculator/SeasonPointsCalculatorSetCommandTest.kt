package tech.coner.trailer.cli.command.seasonpointscalculator

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.RankingSortService
import tech.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import tech.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*

@ExtendWith(MockKExtension::class)
class SeasonPointsCalculatorSetCommandTest : DIAware {

    lateinit var command: SeasonPointsCalculatorSetCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
        bindInstance { view }
        bindInstance { mapper }
    }
    override val diContext = diContext { command.diContext.value }

    private val rankingSortService: RankingSortService by instance()
    private val service: SeasonPointsCalculatorConfigurationService by instance()
    @MockK lateinit var mapper: SeasonPointsCalculatorParameterMapper
    @MockK lateinit var view: SeasonPointsCalculatorConfigurationView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = SeasonPointsCalculatorSetCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should rename a season points calculator`() {
        val current = TestSeasonPointsCalculatorConfigurations.lscc2019
        every { service.findById(current.id) } returns current
        val rename = current.copy(
                name = "rename"
        )
        every { service.update(eq(rename)) } answers { Unit }
        val viewRendered = "view rendered ${rename.name}"
        every { view.render(rename) } returns viewRendered

        command.parse(arrayOf(
                rename.id.toString(),
                "--name", rename.name
        ))

        verifySequence {
            service.findById(rename.id)
            service.update(eq(rename))
            view.render(rename)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }

    @Test
    fun `It should set results type to event points calculator`() {
        val current = TestSeasonPointsCalculatorConfigurations.lscc2019
        every { service.findById(current.id) } returns current
        val rename = current.copy(
                name = "rename"
        )
        every { service.update(eq(rename)) } answers { Unit }
        val viewRendered = "view rendered ${rename.name}"
        every { view.render(rename) } returns viewRendered

        command.parse(arrayOf(
                rename.id.toString(),
                "--name", rename.name
        ))

        verifySequence {
            service.findById(rename.id)
            service.update(eq(rename))
            view.render(rename)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }
}