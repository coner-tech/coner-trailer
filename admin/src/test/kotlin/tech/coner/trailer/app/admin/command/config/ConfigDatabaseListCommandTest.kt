package tech.coner.trailer.app.admin.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.view.DatabaseConfigurationView
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

        val testResult = command.test(emptyArray())

        coVerifySequence {
            service.listDatabases()
            view.render(dbConfigs.all)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEqualTo(output)
        }
    }
}