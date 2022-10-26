package tech.coner.trailer.cli.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.isEmpty
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.clikt.error
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.io.Webapp
import tech.coner.trailer.io.service.ConfigurationService

class ConfigWebappUnsetCommandTest : BaseConfigCommandTest<ConfigWebappUnsetCommand>() {

    override fun createCommand(di: DI, global: GlobalModel) = ConfigWebappUnsetCommand(di, global)

    private val service: ConfigurationService by instance()

    @Test
    fun `It should unset options for results webapp`() {
        coEvery {
            service.configureWebapp(any(), any())
        } returns Result.success(global.requireEnvironment().requireConfiguration())

        command.parse(arrayOf("--webapp", "results"))

        assertThat(testConsole).all {
            output().isEmpty()
            error().isEmpty()
        }
        coVerify {
            service.configureWebapp(Webapp.RESULTS, null)
        }
        confirmVerified(service)
    }
}