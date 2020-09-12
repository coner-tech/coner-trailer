package org.coner.trailer.cli.command.seasonpointscalculator

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import org.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import org.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class SeasonPointsCalculatorGetCommandTest {

    lateinit var command: SeasonPointsCalculatorGetCommand

    @MockK lateinit var service: SeasonPointsCalculatorConfigurationService
    @MockK lateinit var view: SeasonPointsCalculatorConfigurationView

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        console = StringBufferConsole()
        val di = DI {
            bind<SeasonPointsCalculatorConfigurationService>() with instance(service)
            bind<SeasonPointsCalculatorConfigurationView>() with instance(view)
        }
        command = SeasonPointsCalculatorGetCommand(
                di = di,
                useConsole = console
        )
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
        assertThat(console.output).isEqualTo(viewRendered)
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
        assertThat(console.output).isEqualTo(viewRendered)
    }
}