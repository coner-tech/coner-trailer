package tech.coner.trailer.cli.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.isEmpty
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.cli.clikt.error
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.io.Webapp
import tech.coner.trailer.io.service.ConfigurationService

class ConfigWebappUnsetCommandTest : BaseConfigCommandTest<ConfigWebappUnsetCommand>() {

    override fun DirectDI.createCommand() = instance<ConfigWebappUnsetCommand>()

    private val service: ConfigurationService by instance()

    @Test
    fun `It should unset options for competition webapp`() {
        coEvery {
            service.configureWebapp(any(), any())
        } returns Result.success(global.requireEnvironment().requireConfiguration())

        command.parse(arrayOf("--webapp", "competition"))

        assertThat(testConsole).all {
            output().isEmpty()
            error().isEmpty()
        }
        coVerify {
            service.configureWebapp(Webapp.COMPETITION, null)
        }
        confirmVerified(service)
    }
}