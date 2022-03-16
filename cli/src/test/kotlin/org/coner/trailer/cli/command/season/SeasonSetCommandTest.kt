package org.coner.trailer.cli.command.season

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.coner.trailer.TestSeasons
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.command.AbstractCommandTest
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.view.SeasonView
import org.coner.trailer.di.EnvironmentScope
import org.coner.trailer.di.mockkDatabaseModule
import org.coner.trailer.io.TestEnvironments
import org.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import org.coner.trailer.io.service.SeasonService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.*
import java.util.logging.Logger.global

@ExtendWith(MockKExtension::class)
class SeasonSetCommandTest : DIAware {

    lateinit var command: SeasonSetCommand

    override val di = DI.lazy {
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
        command = SeasonSetCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should set a season name`() {
        val season = TestSeasons.lscc2019.copy(
                seasonEvents = emptyList() // https://github.com/caeos/coner-trailer/issues/27
        )
        val update = season.copy(
                name = "renamed"
        )
        every { service.findById(season.id) } returns season
        justRun { service.update(update) }
        val viewRendered = "view rendered"
        every { view.render(update) } returns viewRendered

        command.parse(arrayOf(
                "${update.id}",
                "--name", update.name
        ))

        verifySequence {
            service.findById(update.id)
            service.update(update)
            view.render(update)
        }
        assertThat(testConsole.output, "console output").isEqualTo(viewRendered)
    }
}