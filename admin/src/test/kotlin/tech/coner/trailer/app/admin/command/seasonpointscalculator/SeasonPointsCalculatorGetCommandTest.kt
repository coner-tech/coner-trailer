package tech.coner.trailer.app.admin.command.seasonpointscalculator

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.app.admin.view.SeasonPointsCalculatorConfigurationView
import tech.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import tech.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations

class SeasonPointsCalculatorGetCommandTest : BaseDataSessionCommandTest<SeasonPointsCalculatorGetCommand>() {

    private val service: SeasonPointsCalculatorConfigurationService by instance()
    private val view: SeasonPointsCalculatorConfigurationView by instance()

    override fun DirectDI.createCommand() = instance<SeasonPointsCalculatorGetCommand>()

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