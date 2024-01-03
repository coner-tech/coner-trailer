package tech.coner.trailer.app.admin.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isZero
import com.github.ajalt.clikt.testing.test
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stderr
import tech.coner.trailer.app.admin.clikt.stdout
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

        val testResult = command.test(arrayOf(dbConfig.name))

        coVerifySequence {
            service.removeDatabase(dbConfig.name)
        }
        assertThat(testResult).all {
            statusCode().isZero()
            stdout().isEmpty()
            stderr().isEmpty()
        }
    }

    @Test
    fun `When given invalid name option it should fail`() {
        val exception = Exception("No database found with name")
        coEvery { service.removeDatabase(any()) } returns Result.failure(exception)
        arrangeDefaultErrorHandling()

        val testResult = command.test(arrayOf("baz"))

        verifyDefaultErrorHandlingInvoked(testResult, exception)
        coVerifySequence {
            service.removeDatabase("baz")
        }
        confirmVerified(service, view)
    }
}
