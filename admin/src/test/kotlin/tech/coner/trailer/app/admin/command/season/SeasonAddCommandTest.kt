package tech.coner.trailer.app.admin.command.season

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.every
import io.mockk.justRun
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.TestSeasons
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.command.BaseDataSessionCommandTest
import tech.coner.trailer.app.admin.view.SeasonView
import tech.coner.trailer.io.service.SeasonPointsCalculatorConfigurationService
import tech.coner.trailer.io.service.SeasonService
import tech.coner.trailer.seasonpoints.TestSeasonPointsCalculatorConfigurations

class SeasonAddCommandTest : BaseDataSessionCommandTest<SeasonAddCommand>() {

    private val service: SeasonService by instance()
    private val spccService: SeasonPointsCalculatorConfigurationService by instance()
    private val view: SeasonView by instance()

    override fun DirectDI.createCommand() = instance<SeasonAddCommand>()

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

        val testResult = command.test(arrayOf(
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
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(viewRendered)
        }
    }
}