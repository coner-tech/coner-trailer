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
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.clikt.error
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.WebappConfigurationView
import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.Webapp
import tech.coner.trailer.io.service.ConfigurationService

class ConfigWebappGetCommandTest : BaseConfigCommandTest<ConfigWebappGetCommand>() {

    val service: ConfigurationService by instance()
    val view: WebappConfigurationView by instance()

    override fun createCommand(di: DI, global: GlobalModel) = ConfigWebappGetCommand(di, global)

    @Test
    fun `It should get results webapp config`() {
        val webappConfig = Configuration.DEFAULT.requireWebapps().requireCompetition()
        coEvery { service.getWebappConfiguration(Webapp.RESULTS) } returns Result.success(webappConfig)
        val viewRender = "view rendered"
        every { view.render(any()) } returns viewRender

        command.parse(arrayOf("results"))

        assertThat(testConsole).all {
            output().isEqualTo(viewRender)
            error().isEmpty()
        }
        coVerifySequence {
            service.getWebappConfiguration(Webapp.RESULTS)
            view.render(Webapp.RESULTS to webappConfig)
        }
        confirmVerified(service, view)
    }
}