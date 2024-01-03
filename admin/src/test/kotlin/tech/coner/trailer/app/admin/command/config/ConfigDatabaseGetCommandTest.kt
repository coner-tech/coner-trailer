package tech.coner.trailer.app.admin.command.config

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isNotZero
import com.github.ajalt.clikt.testing.test
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import io.mockk.every
import org.junit.jupiter.api.Test
import org.kodein.di.DirectDI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stdout
import tech.coner.trailer.app.admin.view.DatabaseConfigurationView
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.service.ConfigurationService
import tech.coner.trailer.io.service.NotFoundException
import java.util.*

class ConfigDatabaseGetCommandTest : BaseConfigCommandTest<ConfigDatabaseGetCommand>() {

    private val service: ConfigurationService by instance()
    private val view: DatabaseConfigurationView by instance()

    override fun DirectDI.createCommand() = instance<ConfigDatabaseGetCommand>()
    private val dbConfigs by lazy { tempEnvironmentTestConfigurations.testDatabaseConfigurations }

    @Test
    fun `When given valid name option it should get it`() {
        val render = "view.render(foo) => ${UUID.randomUUID()}"
        val dbConfig = dbConfigs.foo
        coEvery { service.findDatabaseByName(any()) } returns Result.success(dbConfig)
        every { view.render(any<DatabaseConfiguration>()) }.returns(render)

        val testResult = command.test(arrayOf(dbConfig.name))

        coVerifySequence {
            service.findDatabaseByName(dbConfig.name)
            view.render(dbConfigs.foo)
        }
        assertThat(testResult).stdout().contains(render)
    }

    @Test
    fun `When given invalid name option it should fail`() {
        val baz = "baz"
        check(!dbConfigs.allByName.contains(baz)) { "Failed prerequisite: test database configs expected not to contain baz database"}
        val exception = NotFoundException("nfe")
        coEvery { service.findDatabaseByName(any()) } returns Result.failure(exception)
        arrangeDefaultErrorHandling()

        val testResult = command.test(arrayOf(baz))

        assertThat(testResult).statusCode().isNotZero()
        coVerifySequence {
            service.findDatabaseByName(baz)
        }
        confirmVerified(service, view)
        verifyDefaultErrorHandlingInvoked(testResult, exception)
    }
}
