package tech.coner.trailer.cli.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEmpty
import com.github.ajalt.clikt.core.ProgramResult
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import io.mockk.every
import java.util.UUID
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.clikt.error
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.service.ConfigurationService
import tech.coner.trailer.io.service.NotFoundException

class ConfigDatabaseGetCommandTest : BaseConfigCommandTest<ConfigDatabaseGetCommand>() {

    private val service: ConfigurationService by instance()
    private val view: DatabaseConfigurationView by instance()

    override fun createCommand(di: DI, global: GlobalModel) = ConfigDatabaseGetCommand(di, global)
    private val dbConfigs by lazy { tempEnvironmentTestConfigurations.testDatabaseConfigurations }

    @Test
    fun `When given valid name option it should get it`() {
        val render = "view.render(foo) => ${UUID.randomUUID()}"
        val dbConfig = dbConfigs.foo
        coEvery { service.findDatabaseByName(any()) } returns Result.success(dbConfig)
        every { view.render(any<DatabaseConfiguration>()) }.returns(render)

        command.parse(arrayOf(dbConfig.name))

        coVerifySequence {
            service.findDatabaseByName(dbConfig.name)
            view.render(dbConfigs.foo)
        }
        assertThat(testConsole).output().contains(render)
    }

    @Test
    fun `When given invalid name option it should fail`() {
        val baz = "baz"
        check(!dbConfigs.allByName.contains(baz)) { "Failed prerequisite: test database configs expected not to contain baz database"}
        coEvery { service.findDatabaseByName(any()) } returns Result.failure(NotFoundException("nfe"))

        assertThrows<ProgramResult> {
            command.parse(arrayOf(baz))
        }

        coVerifySequence {
            service.findDatabaseByName(baz)
        }
        confirmVerified(service, view)
        assertThat(testConsole).error().all {
            contains("Failed to find database by name")
            contains("nfe")
        }
        assertThat(testConsole).output().isEmpty()
    }
}
