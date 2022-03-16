package org.coner.trailer.cli.command.seasonpointscalculator

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.command.AbstractCommandTest
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import org.coner.trailer.di.EnvironmentScope
import org.coner.trailer.di.mockkDatabaseModule
import org.coner.trailer.io.TestEnvironments
import org.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import org.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import java.util.logging.Logger.global

@ExtendWith(MockKExtension::class)
class SeasonPointsCalculatorGetCommandTest : DIAware {

    lateinit var command: SeasonPointsCalculatorGetCommand

    override val di = DI.lazy {
        import(mockkDatabaseModule())
        bindInstance { view }
    }
    override val diContext = diContext { command.diContext.value }

    private val service: SeasonPointsCalculatorConfigurationService by instance()
    @MockK lateinit var view: SeasonPointsCalculatorConfigurationView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply { environment = TestEnvironments.mock() }
        command = SeasonPointsCalculatorGetCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should get by id`() {
        val get = TestSeasonPointsCalculatorConfigurations.lscc2019
        every { service.findById(eq(get.id)) } returns get
        val viewRendered = "view rendered ${get.name}"
        every { view.render(get) } returns viewRendered

        command.parse(arrayOf(
                "--id", get.id.toString()
        ))

        verifySequence {
            service.findById(eq(get.id))
            view.render(get)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }

    @Test
    fun `It should get by name`() {
        val get = TestSeasonPointsCalculatorConfigurations.lscc2019
        every { service.findByName(get.name) } returns get
        val viewRendered = "view rendered ${get.name}"
        every { view.render(get) } returns viewRendered

        command.parse(arrayOf(
                "--name", get.name
        ))

        verifySequence {
            service.findByName(get.name)
            view.render(get)
        }
        assertThat(testConsole.output).isEqualTo(viewRendered)
    }
}