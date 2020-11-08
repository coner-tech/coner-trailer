package org.coner.trailer.cli.command.season

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import org.coner.trailer.TestSeasons
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.SeasonView
import org.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import org.coner.trailer.io.service.SeasonService
import org.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class SeasonAddCommandTest {

    lateinit var command: SeasonAddCommand

    @MockK lateinit var service: SeasonService
    @MockK lateinit var spccService: SeasonPointsCalculatorConfigurationService
    @MockK lateinit var view: SeasonView

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        val di = DI {
            bind<SeasonService>() with instance(service)
            bind<SeasonPointsCalculatorConfigurationService>() with instance(spccService)
            bind<SeasonView>() with instance(view)
        }
        console = StringBufferConsole()
        command = SeasonAddCommand(
                di = di,
                useConsole = console
        )
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
        assertThat(console.output, "console output").isEqualTo(viewRendered)
    }
}