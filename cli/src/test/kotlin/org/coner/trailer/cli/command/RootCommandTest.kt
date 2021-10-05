package org.coner.trailer.cli.command

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.subcommands
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.command.config.ConfigCommand
import org.coner.trailer.cli.di.ConfigurationServiceArgument
import org.coner.trailer.cli.io.TestDatabaseConfigurations
import org.coner.trailer.io.ConfigurationService
import org.coner.trailer.io.DatabaseConfiguration
import org.coner.trailer.cli.service.StubService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import java.nio.file.Path
import kotlin.io.path.createDirectory

@ExtendWith(MockKExtension::class)
class RootCommandTest {

    lateinit var command: RootCommand

    @MockK lateinit var serviceFactory: (ConfigurationServiceArgument) -> ConfigurationService
    @MockK lateinit var service: ConfigurationService
    @MockK lateinit var noDatabase: DatabaseConfiguration
    @MockK lateinit var stubService: StubService

    @TempDir
    lateinit var temp: Path

    lateinit var testConsole: StringBufferConsole
    lateinit var dbConfigs: TestDatabaseConfigurations

    lateinit var serviceArgumentSlot: CapturingSlot<ConfigurationServiceArgument>

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        serviceArgumentSlot = slot()
        every { service.noDatabase } returns noDatabase
        justRun { service.setup() }
        justRun { stubService.doSomething() }
        dbConfigs = TestDatabaseConfigurations(temp)
        val di = DI {
            bind<ConfigurationService>() with factory { csa: ConfigurationServiceArgument ->
                serviceArgumentSlot.captured = csa
                service
            }
            bind<StubService>() with instance(stubService)
        }
        command = RootCommand(di).context {
            console = testConsole
        }
        command.subcommands(
            StubCommand(di),
            ConfigCommand()
        )
    }

    @Test
    fun `When given --database with existing database name it should use it`() {
        arrangeWithDatabases()
        every { service.listDatabasesByName()}.returns(dbConfigs.allByName)

        command.parse(arrayOf(
            "--database", "foo",
            "stub"
        ))

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

        val actual = assertThrows<Abort> {
            command.parse(arrayOf(
                "--database", "baz",
                "stub"
            ))
        }

        assertThat(testConsole.error).all {
            contains("Database not found")
            doesNotContain("baz")
        }
    }

    @Test
    fun `When not given --database it should use the default if available`() {
        arrangeWithDatabases()
        val defaultDatabase = dbConfigs.allByName
                .values
                .single { it.default }

        command.parse(arrayOf("stub"))

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

        assertThat(testConsole.error).all {
            contains("No database chosen and no default configured.")
            contains("coner-trailer-cli config database")
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
        arrangeWithDatabases()

        command.parse(arrayOf("stub"))
        val actualServiceArgument = serviceArgumentSlot.captured

        assertThat(actualServiceArgument, "service factory argument")
            .isSameAs(ConfigurationServiceArgument.Default)
    }

    @Test
    fun `When passed --config-dir, it should use override ConfigurationService`() {
        arrangeWithDatabases()
        val overrideConfigDir = temp.resolve("override-config-dir").apply {
            createDirectory()
        }

        command.parse(arrayOf(
            "--config-dir", "$overrideConfigDir",
            "stub"
        ))
        val actualServiceArgument = serviceArgumentSlot.captured

        assertThat(actualServiceArgument, "service factory argument")
            .isInstanceOf(ConfigurationServiceArgument.Override::class)
            .prop("configDir", ConfigurationServiceArgument.Override::configDir)
            .isEqualTo(overrideConfigDir)
    }

    private fun arrangeWithDatabases() {
        every { service.listDatabasesByName() } answers { dbConfigs.allByName }
        every { service.getDefaultDatabase() } returns(dbConfigs.bar)
    }

    private fun arrangeWithoutDatabasesCase() {
        every { service.listDatabasesByName() } answers { mapOf(
            noDatabase.name to noDatabase
        ) }
        every { service.getDefaultDatabase() } returns null
    }
}
