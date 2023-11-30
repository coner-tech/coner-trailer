package tech.coner.trailer.app.admin.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEmpty
import com.github.ajalt.clikt.core.ProgramResult
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.clikt.error
import tech.coner.trailer.app.admin.clikt.output
import tech.coner.trailer.app.admin.view.DatabaseConfigurationView
import tech.coner.trailer.io.service.ConfigurationService

class ConfigDatabaseRemoveCommandTest : BaseConfigCommandTest<ConfigDatabaseRemoveCommand>() {

    private val service: ConfigurationService by instance()
    private val view: DatabaseConfigurationView by instance()

    private val config by lazy { tempEnvironmentConfiguration }
    private val dbConfig by lazy { tempEnvironmentTestConfigurations.testDatabaseConfigurations.foo }

    override fun DirectDI.createCommand() = instance<ConfigDatabaseRemoveCommand>()

    @Test
    fun `When given valid name option it should remove named database`() {
        val newConfig = config.copy(
            databases = emptyMap()
        )
        coEvery { service.removeDatabase(any()) } returns Result.success(newConfig)

        command.parse(arrayOf(dbConfig.name))

        coVerifySequence {
            service.removeDatabase(dbConfig.name)
        }
        assertThat(testConsole).all {
            output().isEmpty()
            error().isEmpty()
        }
    }

    @Test
    fun `When given invalid name option it should fail`() {
        val exception = Exception("No database found with name")
        coEvery { service.removeDatabase(any()) } returns Result.failure(exception)
        arrangeDefaultErrorHandling()

        assertThrows<ProgramResult> {
            command.parse(arrayOf("baz"))
        }

        verifyDefaultErrorHandlingInvoked(exception)
        coVerifySequence {
            service.removeDatabase("baz")
        }
        confirmVerified(service, view)
    }
}
