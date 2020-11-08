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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class SeasonSetCommandTest {

    lateinit var command: SeasonSetCommand

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
        command = SeasonSetCommand(
                di = di,
                useConsole = console
        )
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
        assertThat(console.output, "console output").isEqualTo(viewRendered)
    }
}