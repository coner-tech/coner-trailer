package org.coner.trailer.cli.command

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.BadParameterValue
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.output.CliktConsole
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.command.config.ConfigCommand
import org.coner.trailer.cli.di.ConfigurationServiceArgument
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.io.TestDatabaseConfigurations
import org.coner.trailer.cli.service.StubService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import java.nio.file.Path

@ExtendWith(MockKExtension::class)
class RootCommandTest {

    lateinit var command: RootCommand

    @MockK lateinit var serviceFactory: (ConfigurationServiceArgument) -> ConfigurationService
    @MockK lateinit var service: ConfigurationService
    @MockK lateinit var noDatabase: DatabaseConfiguration

    @TempDir
    lateinit var temp: Path

    lateinit var console: StringBufferConsole
    lateinit var dbConfigs: TestDatabaseConfigurations

    lateinit var serviceArgumentSlot: CapturingSlot<ConfigurationServiceArgument>

    @BeforeEach
    fun before() {
        console = StringBufferConsole()
        serviceArgumentSlot = slot()
        every { serviceFactory(capture(slot())) } returns service
        every { service.noDatabase } returns noDatabase
        dbConfigs = TestDatabaseConfigurations(temp)
    }

    @Test
    fun `When given --database with existing database name it should use it`() {
        arrangeWithDatabases()
        every { service.listDatabasesByName()}.returns(dbConfigs.allByName)

        command.parse(arrayOf("--database", "foo"))

        assertThat(command.currentContext.obj)
                .isNotNull()
                .isInstanceOf(DI::class)
                .transform { it.direct.instance<DatabaseConfiguration>() }
                .isSameAs(dbConfigs.foo)
    }

    @Test
    fun `When given --database with invalid name it should fail`() {
        arrangeWithDatabases()
        // baz does not exist

        val actual = assertThrows<BadParameterValue> {
            command.parse(arrayOf("--database", "baz"))
        }

        assertThat(actual).all {
            messageContains("--database")
            messageContains("baz")
        }
    }

    @Test
    fun `When not given --database it should use the default if available`() {
        arrangeWithDatabases()
        val defaultDatabase = dbConfigs.allByName
                .values
                .single { it.default }

        command.parse(emptyArray())

        verify { service.getDefaultDatabase() }
        assertThat(command.currentContext.obj)
                .isNotNull()
                .isInstanceOf(DI::class)
                .transform { it.direct.instance<DatabaseConfiguration>() }
                .isSameAs(defaultDatabase)
    }

    @Test
    fun `When no database chosen and subcommand requires database choice, it should abort`() {
        arrangeWithoutDatabasesCase()

        assertThrows<Abort> {
            command.parse(arrayOf("stub"))
        }

        assertThat(console.output).all {
            contains("No database chosen and no default configured.")
            contains("coner-trailer config database")
        }
    }

    @Test
    fun `When no database chosen and subcommand permits no database, it should continue without database`() {
        arrangeWithoutDatabasesCase()

        command.parse(arrayOf("config"))

        assertThat(command.currentContext.obj)
                .isNotNull()
                .isInstanceOf(DI::class)
                .given { di: DI ->
                    assertThrows<DI.NotFoundException> { di.direct.instance<DatabaseConfiguration>() }
                }
    }

    @Test
    fun `When not passed --config-dir, it should use default ConfigurationService`() {
        TODO()
    }

    @Test
    fun `When passed --config-dir, it should use override ConfigurationService`() {
        TODO()
    }
}

private fun RootCommandTest.arrangeWithDatabases() {
    justRun { service.setup() }
    every { service.listDatabasesByName() } answers { dbConfigs.allByName }
    every { service.getDefaultDatabase() } returns(dbConfigs.bar)
    command = RootCommand(DI {
        bind<ConfigurationService>() with factory { service }
    })
}

private fun RootCommandTest.arrangeWithoutDatabasesCase() {
    justRun { service.setup() }
    every { service.listDatabasesByName() } answers { mapOf(
            noDatabase.name to noDatabase
    ) }
    every { service.getDefaultDatabase() } returns null
    command = RootCommand(DI {
        bind<ConfigurationService>() with factory { service }
    })
}