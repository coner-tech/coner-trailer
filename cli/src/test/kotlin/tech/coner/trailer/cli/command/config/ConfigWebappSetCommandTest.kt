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
import tech.coner.trailer.io.Webapp
import tech.coner.trailer.io.WebappConfiguration
import tech.coner.trailer.io.service.ConfigurationService

class ConfigWebappSetCommandTest : BaseConfigCommandTest<ConfigWebappSetCommand>() {

    private val service: ConfigurationService by instance()
    private val view: WebappConfigurationView by instance()

    override fun DirectDI.createCommand() = instance<ConfigWebappSetCommand>()

    @Test
    fun `It should set options for competition webapp`() {
        val newWebappConfig = WebappConfiguration(
            port = 12345,
            exploratory = false
        )
        val newConfig = global.requireEnvironment().requireConfiguration().let { oldConfig ->
            oldConfig.copy(
                webapps = oldConfig.requireWebapps().copy(
                    competition = newWebappConfig
                )
            )
        }
        coEvery { service.configureWebapp(any(), any()) } returns Result.success(newConfig)
        val viewRender = "view render"
        every { view.render(any()) } returns viewRender

        command.parse(arrayOf(
            "competition",
            "--port", "${newWebappConfig.port}"
        ))

        assertThat(testConsole).all {
            output().isEqualTo(viewRender)
            error().isEmpty()
        }
        coVerifySequence {
            service.configureWebapp(Webapp.COMPETITION, newWebappConfig)
            view.render(Webapp.COMPETITION to newWebappConfig)
        }
        confirmVerified(service, view)
    }
}