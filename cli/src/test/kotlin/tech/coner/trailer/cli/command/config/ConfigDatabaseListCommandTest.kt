package tech.coner.trailer.cli.command.config

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import java.util.UUID
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.io.TestDatabaseConfigurations
import tech.coner.trailer.io.service.ConfigurationService

class ConfigDatabaseListCommandTest : BaseConfigCommandTest<ConfigDatabaseListCommand>() {

    private val service: ConfigurationService by instance()
    private val view: DatabaseConfigurationView by instance()

    private val dbConfigs: TestDatabaseConfigurations by lazy { tempEnvironmentTestConfigurations.testDatabaseConfigurations }

    override fun createCommand(di: DI, global: GlobalModel) = ConfigDatabaseListCommand(di, global)

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