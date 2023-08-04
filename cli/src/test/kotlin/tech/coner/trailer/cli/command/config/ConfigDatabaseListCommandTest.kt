package tech.coner.trailer.cli.command.config

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.io.TestDatabaseConfigurations
import tech.coner.trailer.io.service.ConfigurationService
import java.util.*

class ConfigDatabaseListCommandTest : BaseConfigCommandTest<ConfigDatabaseListCommand>() {

    private val service: ConfigurationService by instance()
    private val view: DatabaseConfigurationView by instance()

    private val dbConfigs: TestDatabaseConfigurations by lazy { tempEnvironmentTestConfigurations.testDatabaseConfigurations }

    override fun DirectDI.createCommand() = instance<ConfigDatabaseListCommand>()

    @Test
    fun `It should list databases`() {
        coEvery { service.listDatabases() } returns Result.success(dbConfigs.all)
        val output = """
            foo => ${UUID.randomUUID()}
            bar => ${UUID.randomUUID()}
        """.trimIndent()
        every { view.render(dbConfigs.all) } returns(output)

        command.parse(emptyArray())

        coVerifySequence {
            service.listDatabases()
            view.render(dbConfigs.all)
        }
        assertThat(testConsole).output().isEqualTo(output)
    }
}