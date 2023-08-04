package tech.coner.trailer.cli.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import io.mockk.every
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.cli.clikt.error
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.view.WebappConfigurationView
import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.Webapp
import tech.coner.trailer.io.service.ConfigurationService

class ConfigWebappGetCommandTest : BaseConfigCommandTest<ConfigWebappGetCommand>() {

    val service: ConfigurationService by instance()
    val view: WebappConfigurationView by instance()

    override fun DirectDI.createCommand() = instance<ConfigWebappGetCommand>()

    @Test
    fun `It should get competition webapp config`() {
        val webappConfig = Configuration.DEFAULT.requireWebapps().requireCompetition()
        coEvery { service.getWebappConfiguration(Webapp.COMPETITION) } returns Result.success(webappConfig)
        val viewRender = "view rendered"
        every { view.render(any()) } returns viewRender

        command.parse(arrayOf("competition"))

        assertThat(testConsole).all {
            output().isEqualTo(viewRender)
            error().isEmpty()
        }
        coVerifySequence {
            service.getWebappConfiguration(Webapp.COMPETITION)
            view.render(Webapp.COMPETITION to webappConfig)
        }
        confirmVerified(service, view)
    }
}