package tech.coner.trailer.cli.command.config

import assertk.all
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.ProgramResult
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.cli.clikt.error
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.view.DatabaseConfigurationView
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

        command.parse(arrayOf(originalFooDbConfig.name))

        coVerifySequence {
            service.setDefaultDatabase(originalFooDbConfig.name)
            view.render(expectedDbConfig)
        }
        assertAll {
            assertThat(slot.captured, "database name passed").isEqualTo(expectedDbConfig.name)
            assertThat(testConsole).output().isEqualTo(viewRender)
        }
    }

    @Test
    fun `When given invalid name it should fail`() {
        val exception = NotFoundException("Not found")
        coEvery { service.setDefaultDatabase(any()) } returns Result.failure(exception)
        val name = "irrelevant"

        val actual = assertThrows<ProgramResult> {
            command.parse(arrayOf(name))
        }

        coVerifySequence { service.setDefaultDatabase(name) }
        verifySequence(inverse = true) { view.render(any<DatabaseConfiguration>()) }
        assertThat(testConsole)
            .error()
            .all {
                contains("Failed to set default database")
                contains(exception.message!!)
            }
    }
}
