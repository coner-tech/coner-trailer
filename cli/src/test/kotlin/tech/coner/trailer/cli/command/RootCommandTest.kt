package tech.coner.trailer.cli.command

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.subcommands
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.config.ConfigCommand
import tech.coner.trailer.cli.service.StubService
import tech.coner.trailer.di.ConfigurationServiceArgument
import tech.coner.trailer.di.EnvironmentScope
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.io.ConfigurationService
import tech.coner.trailer.io.TestDatabaseConfigurations
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import java.nio.file.Path
import kotlin.io.path.createDirectory

@ExtendWith(MockKExtension::class)
class RootCommandTest : DIAware {

    lateinit var command: RootCommand

    override val di: DI = DI.lazy {
        import(mockkDatabaseModule())
        bind<ConfigurationService>() with factory { csa: ConfigurationServiceArgument ->
            serviceArgumentSlot.captured = csa
            service
        }
        bind { scoped(EnvironmentScope).singleton { stubService } }
    }

    lateinit var global: GlobalModel
    lateinit var testConsole: StringBufferConsole

    @MockK lateinit var service: ConfigurationService
    @MockK lateinit var stubService: StubService

    @TempDir lateinit var temp: Path

    lateinit var dbConfigs: TestDatabaseConfigurations
    lateinit var serviceArgumentSlot: CapturingSlot<ConfigurationServiceArgument>

    @BeforeEach
    fun before() {
        global = GlobalModel()
        testConsole = StringBufferConsole()
        command = RootCommand(di, global)
            .context { console = testConsole }
            .subcommands(
                StubCommand(di, global),
                ConfigCommand()
            )
        serviceArgumentSlot = slot()
        justRun { service.setup() }
        justRun { stubService.doSomething() }
        dbConfigs = TestDatabaseConfigurations(temp)
    }

    @Test
    fun `When given --database with existing database name it should use it`() {
        arrangeWithDatabases()
        every { service.listDatabasesByName()}.returns(dbConfigs.allByName)

        command.parse(arrayOf(
            "--database", "foo",
            "stub"
        ))

        assertThat(global.environment)
            .isNotNull()
            .transform { it.databaseConfiguration }
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
        assertThat(global.environment)
            .isNotNull()
            .transform { it.databaseConfiguration }
            .isSameAs(defaultDatabase)
    }

    @Test
    fun `When no database chosen and subcommand requires database choice, it should abort`() {
        arrangeWithoutDatabasesCase()

        assertThrows<Abort> {
            command.parse(arrayOf("stub"))
        }

        assertThat(testConsole.error).all {
            contains("Command requires database but no database was selected.")
            contains("coner-trailer-cli config database")
        }
    }

    @Test
    fun `When no database chosen and subcommand permits no database, it should continue without database`() {
        arrangeWithoutDatabasesCase()

        command.parse(arrayOf("config"))

        assertThat(global.environment)
            .isNotNull()
            .transform { it.databaseConfiguration }
            .isNull()
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
        every { service.listDatabasesByName() } answers { emptyMap() }
        every { service.getDefaultDatabase() } returns null
    }
}
