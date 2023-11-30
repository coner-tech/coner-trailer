package tech.coner.trailer.app.admin.command

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.isSameAs
import com.github.ajalt.clikt.core.PrintHelpMessage
import com.github.ajalt.clikt.core.ProgramResult
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import java.nio.file.Path
import kotlin.io.path.createDirectory
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bindMultiton
import org.kodein.di.bindSingleton
import org.kodein.di.direct
import org.kodein.di.factory
import org.kodein.di.instance
import tech.coner.trailer.app.admin.clikt.command
import tech.coner.trailer.app.admin.command.config.ConfigCommand
import tech.coner.trailer.app.admin.di.cliktModule
import tech.coner.trailer.app.admin.di.command.commandModule
import tech.coner.trailer.app.admin.di.mockkCliPresentationAdapterModule
import tech.coner.trailer.app.admin.di.mockkCliPresentationViewModule
import tech.coner.trailer.di.ConfigurationServiceArgument
import tech.coner.trailer.di.configDir
import tech.coner.trailer.di.isDefaultInstance
import tech.coner.trailer.di.isOverrideInstance
import tech.coner.trailer.di.mockkServiceModule
import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.TestConfigurations
import tech.coner.trailer.io.TestDatabaseConfigurations
import tech.coner.trailer.io.service.ConfigurationService

@ExtendWith(MockKExtension::class)
class RootCommandTest : AbstractCommandTest<RootCommand>() {

    @TempDir lateinit var temp: Path

    lateinit var config: Configuration
    lateinit var dbConfigs: TestDatabaseConfigurations
    lateinit var expectConfigurationServiceArgument: ConfigurationServiceArgument
    lateinit var actualConfigurationServiceArgument: ConfigurationServiceArgument
    lateinit var service: ConfigurationService

    override val di = DI {
        fullContainerTreeOnError = true
        fullDescriptionOnError = true
        bindSingleton { di }
        importAll(
            mockkServiceModule, // TODO: eliminate, command to interact with presenter only
            mockkCliPresentationViewModule,
            mockkCliPresentationAdapterModule,
            cliktModule,
            commandModule
        )
        bindMultiton<ConfigurationServiceArgument, ConfigurationService>(overrides = true) { argument ->
            actualConfigurationServiceArgument = argument
            mockk()
        }
    }

    override fun DirectDI.createCommand() = instance<RootCommand>()

    override val setupGlobal: GlobalModel.() -> Unit
        get() = { /* no-op */ }

    override fun postSetup() {
        super.postSetup()
        val configs = TestConfigurations(temp)
        config = configs.testConfiguration()
        dbConfigs = configs.testDatabaseConfigurations
    }

    @Test
    fun `When given --database with existing database name it should use it`() = runTest {
        arrangeWithDatabases()
        coEvery { service.findDatabaseByName(any()) }.returns(Result.success((dbConfigs.foo)))

        assertFailure {
            command.parse(arrayOf(
                "--database", "foo",
                "config"
            ))
        }
            .isInstanceOf<PrintHelpMessage>()
            .command()
            .isInstanceOf<ConfigCommand>()

        assertThat(actualConfigurationServiceArgument).isEqualTo(expectConfigurationServiceArgument)
        assertThat(global.environment)
            .isNotNull()
            .transform { it.databaseConfiguration }
            .isSameAs(dbConfigs.foo)
    }

    @Test
    fun `When given --database with invalid name it should fail`() = runTest {
        arrangeWithDatabases()
        val exception = Exception("Database with name not found")
        coEvery { service.findDatabaseByName(any()) } returns Result.failure(exception)
        arrangeDefaultErrorHandling()

        // baz does not exist
        assertFailure {
            command.parse(arrayOf(
                "--database", "baz",
                "config"
            ))
        }
            .isInstanceOf<ProgramResult>()

        assertThat(actualConfigurationServiceArgument).isEqualTo(expectConfigurationServiceArgument)
        coVerifySequence {
            service.init()
            service.findDatabaseByName("baz")
        }
        verifyDefaultErrorHandlingInvoked(exception)
    }

    @Test
    fun `When not given --database it should use the default if available`() = runTest {
        arrangeWithDatabases()
        val defaultDatabase = dbConfigs.allByName
            .values
            .single { it.default }

        assertFailure {
            command.parse(arrayOf("config"))
        }
            .isInstanceOf<PrintHelpMessage>()
            .command()
            .isInstanceOf<ConfigCommand>()

        assertThat(actualConfigurationServiceArgument).isEqualTo(expectConfigurationServiceArgument)
        coVerify { service.getDefaultDatabase() }
        assertThat(global.environment)
            .isNotNull()
            .transform { it.databaseConfiguration }
            .isSameAs(defaultDatabase)
    }

    @Test
    fun `When no database chosen and subcommand requires database choice, it should abort`() {
        arrangeWithoutDatabasesCase()
        arrangeDefaultErrorHandling()

        assertThrows<ProgramResult> {
            command.parse(arrayOf("club"))
        }

        assertThat(actualConfigurationServiceArgument).isEqualTo(expectConfigurationServiceArgument)
        verifyDefaultErrorHandlingInvoked(NoDatabaseChosenException())
    }

    @Test
    fun `When no database chosen and subcommand permits no database, it should continue without database`() {
        arrangeWithoutDatabasesCase()

        assertFailure {
            command.parse(arrayOf("config"))
        }
            .isInstanceOf<PrintHelpMessage>()
            .command()
            .isInstanceOf<ConfigCommand>()

        assertThat(actualConfigurationServiceArgument).isEqualTo(expectConfigurationServiceArgument)
        assertThat(global.environment)
            .isNotNull()
            .transform { it.databaseConfiguration }
            .isNull()
    }

    @Test
    fun `When not passed --config-dir, it should use default ConfigurationService`() {
        arrangeWithDatabases()

        assertFailure {
            command.parse(emptyArray())
        }
            .isInstanceOf<PrintHelpMessage>()
            .command()
            .isInstanceOf<RootCommand>()

        assertThat(actualConfigurationServiceArgument)
            .isDefaultInstance()
    }

    @Test
    fun `When passed --config-dir, it should use override ConfigurationService`() {
        val overrideConfigDir = temp.resolve("override-config-dir").apply {
            createDirectory()
        }
        arrangeWithDatabases(overrideConfigDir)

        assertFailure {
            command.parse(arrayOf(
                "--config-dir", "$overrideConfigDir",
            ))
        }
            .isInstanceOf<PrintHelpMessage>()
            .command()
            .isInstanceOf<RootCommand>()

        assertThat(actualConfigurationServiceArgument, "service factory argument")
            .isOverrideInstance()
            .configDir()
            .isEqualTo(overrideConfigDir)
    }

    private fun arrangeWithDatabases(
        configDir: Path? = null
    ) = runTest {
        arrangeConfigurationService(configDir)
        coEvery { service.get() }.returns(Result.success(config))
        coEvery { service.listDatabasesByName() } answers { Result.success(dbConfigs.allByName) }
        coEvery { service.getDefaultDatabase() } returns(Result.success(dbConfigs.bar))
    }

    private fun arrangeWithoutDatabasesCase(
        configDir: Path? = null
    ) {
        arrangeConfigurationService(configDir)
        coEvery { service.get() }.returns(Result.success(Configuration.DEFAULT))
        coEvery { service.listDatabasesByName() } answers { Result.success(emptyMap()) }
        coEvery { service.getDefaultDatabase() } returns Result.success(null)
    }

    private fun arrangeConfigurationService(
        configDir: Path? = null
    ) {
        val configurationServiceArgument = configDir?.let { ConfigurationServiceArgument.Override(it) }
            ?: ConfigurationServiceArgument.Default
        expectConfigurationServiceArgument = configurationServiceArgument
        service = direct.factory<ConfigurationServiceArgument, ConfigurationService>()
            .invoke(configurationServiceArgument)
        coJustRun { service.init() }
    }
}
