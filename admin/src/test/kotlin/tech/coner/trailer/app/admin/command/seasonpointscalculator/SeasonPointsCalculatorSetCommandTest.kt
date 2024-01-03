package tech.coner.trailer.app.admin.command.seasonpointscalculator

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.every
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.app.admin.view.SeasonPointsCalculatorConfigurationView
import tech.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import tech.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations

class SeasonPointsCalculatorSetCommandTest : BaseDataSessionCommandTest<SeasonPointsCalculatorSetCommand>() {

    private val service: SeasonPointsCalculatorConfigurationService by instance()
    private val view: SeasonPointsCalculatorConfigurationView by instance()

    override fun DirectDI.createCommand() = instance<SeasonPointsCalculatorSetCommand>()

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

        val testResult = command.test(arrayOf(
                rename.id.toString(),
                "--name", rename.name
        ))

        verifySequence {
            service.findById(rename.id)
            service.update(eq(rename))
            view.render(rename)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
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

        val testResult = command.test(arrayOf(
                rename.id.toString(),
                "--name", rename.name
        ))

        verifySequence {
            service.findById(rename.id)
            service.update(eq(rename))
            view.render(rename)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
    }
}
