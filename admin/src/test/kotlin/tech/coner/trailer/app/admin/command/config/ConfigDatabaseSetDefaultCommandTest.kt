package tech.coner.trailer.app.admin.command.config

import assertk.all
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.*
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.view.DatabaseConfigurationView
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.payload.ConfigSetDefaultDatabaseOutcome
import tech.coner.trailer.io.service.ConfigurationService
import tech.coner.trailer.io.service.NotFoundException

class ConfigDatabaseSetDefaultCommandTest : BaseConfigCommandTest<ConfigDatabaseSetDefaultCommand>() {

    private val service: ConfigurationService by instance()
    private val view: DatabaseConfigurationView by instance()

    override fun DirectDI.createCommand() = instance<ConfigDatabaseSetDefaultCommand>()

    @Test
    fun `When given valid name it should set default and succeed`() {
        val slot = slot<String>()
        val originalConfig = global.requireEnvironment().requireConfiguration()
        val originalFooDbConfig = tempEnvironmentTestConfigurations.testDatabaseConfigurations.foo
        check(originalFooDbConfig.name == "foo") { "Prerequisite failed: expected database named foo"}
        check(!originalFooDbConfig.default) { "Prerequisite failed: expected foo database not default"}
        val expectedDbConfig = originalFooDbConfig.copy(
            default = true
        )
        val expectedConfig = originalConfig.copy(
            databases = originalConfig.databases.toMutableMap().apply { put(expectedDbConfig.name, expectedDbConfig) },
            defaultDatabaseName = expectedDbConfig.name
        )
        coEvery {
            service.setDefaultDatabase(capture(slot))
        } returns Result.success(ConfigSetDefaultDatabaseOutcome(expectedConfig, expectedDbConfig))
        val viewRender = "view render"
        every { view.render(any<DatabaseConfiguration>()) } returns viewRender

        val testResult = command.test(arrayOf(originalFooDbConfig.name))

        coVerifySequence {
            service.setDefaultDatabase(originalFooDbConfig.name)
            view.render(expectedDbConfig)
        }
        assertAll {
            assertThat(slot.captured, "database name passed").isEqualTo(expectedDbConfig.name)
            assertThat(testResult).all {
                statusCode().isZero()
                stdout().isEqualTo(viewRender)
            }
        }
    }

    @Test
    fun `When given invalid name it should fail`() {
        val exception = NotFoundException("Not found")
        coEvery { service.setDefaultDatabase(any()) } returns Result.failure(exception)
        val name = "irrelevant"
        arrangeDefaultErrorHandling()

        val testResult = command.test(arrayOf(name))

        verifyDefaultErrorHandlingInvoked(testResult, exception)
        coVerifySequence { service.setDefaultDatabase(name) }
        confirmVerified(service, view)
    }
}
