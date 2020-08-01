package org.coner.trailer.cli.command

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.BadParameterValue
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.output.CliktConsole
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.io.TestDatabaseConfigurations
import org.coner.trailer.cli.service.StubService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import java.io.File

class RootCommandTest {

    lateinit var command: RootCommand

    @RelaxedMockK
    lateinit var config: ConfigurationService
    @RelaxedMockK
    lateinit var noDatabase: DatabaseConfiguration

    @TempDir
    lateinit var temp: File

    lateinit var console: StringBufferConsole
    lateinit var dbConfigs: TestDatabaseConfigurations
    lateinit var di: DI

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        console = StringBufferConsole()
        every { config.noDatabase } returns noDatabase
        dbConfigs = TestDatabaseConfigurations(temp)
    }

    @Test
    fun `When given --database with existing database name it should use it`() {
        arrangeWithDatabases()
        // foo exists
        every { config.listDatabasesByName()}.returns(dbConfigs.allByName)

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

        verify { config.getDefaultDatabase() }
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
}

private fun RootCommandTest.arrangeWithDatabases() {
    di = DI {
        bind<CliktConsole>() with instance(console)
        bind<ConfigurationService>() with instance(config)
        bind<StubService>() with singleton { StubService(
                databaseConfiguration = instance()
        ) }
    }
    every { config.setup() }.answers { Unit }
    every { config.listDatabasesByName() }.answers { dbConfigs.allByName }
    every { config.getDefaultDatabase() }.returns(dbConfigs.bar)
    command = RootCommand(di)
}

private fun RootCommandTest.arrangeWithoutDatabasesCase() {
    di = DI {
        bind<CliktConsole>() with instance(console)
        bind<ConfigurationService>() with instance(config)
        bind<StubService>() with singleton { StubService(
                databaseConfiguration = instance()
        ) }
    }
    every { config.setup() }.answers { Unit }
    every { config.listDatabasesByName() }.answers { mapOf(
            noDatabase.name to noDatabase
    ) }
    every { config.getDefaultDatabase() }.returns(null)
    command = RootCommand(di)
            .subcommands(
                    ConfigCommand(),
                    StubCommand(di)
            )
}