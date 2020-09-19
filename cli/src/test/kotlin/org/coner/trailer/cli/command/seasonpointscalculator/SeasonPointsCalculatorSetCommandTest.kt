package org.coner.trailer.cli.command.seasonpointscalculator

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.view.SeasonPointsCalculatorConfigurationView
import org.coner.trailer.io.service.RankingSortService
import org.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import org.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance

@ExtendWith(MockKExtension::class)
class SeasonPointsCalculatorSetCommandTest {

    lateinit var command: SeasonPointsCalculatorSetCommand

    @MockK
    lateinit var mapper: SeasonPointsCalculatorParameterMapper
    @MockK
    lateinit var rankingSortService: RankingSortService
    @MockK
    lateinit var service: SeasonPointsCalculatorConfigurationService
    @MockK
    lateinit var view: SeasonPointsCalculatorConfigurationView

    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        console = StringBufferConsole()
        val di = DI {
            bind<SeasonPointsCalculatorParameterMapper>() with instance(mapper)
            bind<RankingSortService>() with instance(rankingSortService)
            bind<SeasonPointsCalculatorConfigurationService>() with instance(service)
            bind<SeasonPointsCalculatorConfigurationView>() with instance(view)
        }
        command = SeasonPointsCalculatorSetCommand(
                di = di,
                useConsole = console
        )
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
        assertThat(console.output).isEqualTo(viewRendered)
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
        assertThat(console.output).isEqualTo(viewRendered)
    }
}
