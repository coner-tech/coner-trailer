package tech.coner.trailer.cli.command

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.isSameAs
import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.PrintHelpMessage
import com.github.ajalt.clikt.core.ProgramResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
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
import org.kodein.di.direct
import org.kodein.di.factory
import org.kodein.di.instance
import tech.coner.trailer.cli.service.FeatureService
import tech.coner.trailer.di.ConfigurationServiceArgument
import tech.coner.trailer.di.ConfigurationServiceFactory
import tech.coner.trailer.di.configDir
import tech.coner.trailer.di.isDefaultInstance
import tech.coner.trailer.di.isOverrideInstance
import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.TestConfigurations
import tech.coner.trailer.io.TestDatabaseConfigurations
import tech.coner.trailer.io.service.ConfigurationService

@ExtendWith(MockKExtension::class)
class RootCommandTest : AbstractCommandTest<RootCommand>() {

    @TempDir lateinit var temp: Path

    lateinit var config: Configuration
    lateinit var dbConfigs: TestDatabaseConfigurations
    lateinit var configurationServiceArgument: ConfigurationServiceArgument
    lateinit var serviceFactory: ConfigurationServiceFactory
    lateinit var service: ConfigurationService

    override val extraModule = DI.Module("RootCommandTest.extraModule") {
        bindMultiton<ConfigurationServiceArgument, ConfigurationService>(overrides = true) { argument ->
            configurationServiceArgument = argument
            mockk()
        }
    }

    override fun DirectDI.createCommand() = instance<RootCommand>()

    override val setupGlobal: GlobalModel.() -> Unit
        get() = { /* no-op */ }

    override fun preSetup() {
        super.preSetup()
        val directDi = direct
        val featureService = directDi.instance<FeatureService>()
        every { featureService.get() } returns emptySet()
    }

    override fun postSetup() {
        super.postSetup()
        val directDi = direct
        serviceFactory = directDi.factory()
        service = serviceFactory(ConfigurationServiceArgument.Default)
        justRun { service.init() }
        val configs = TestConfigurations(temp)
        config = configs.testConfiguration()
        dbConfigs = configs.testDatabaseConfigurations
    }



    @Test
    fun `When given --database with existing database name it should use it`() = runTest {
        arrangeWithDatabases()
        coEvery { service.findDatabaseByName(any()) }.returns(Result.success((dbConfigs.foo)))

        command.parse(arrayOf(
            "--database", "foo",
            "config"
        ))

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
        assertThrows<ProgramResult> {
            command.parse(arrayOf(
                "--database", "baz",
                "config"
            ))
        }

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

        assertThrows<PrintHelpMessage> {
            command.parse(arrayOf("config"))
        }

        coVerify { service.getDefaultDatabase() }
        assertThat(global.environment)
            .isNotNull()
            .transform { it.databaseConfiguration }
            .isSameAs(defaultDatabase)
    }

    @Test
    fun `When no database chosen and subcommand requires database choice, it should abort`() {
        arrangeWithoutDatabasesCase()

        assertThrows<Abort> {
            command.parse(arrayOf("club"))
        }

        assertThat(testConsole.error).all {
            contains("Command requires database but no database was selected.")
            contains("coner-trailer-cli config database")
        }
        verifyDefaultErrorHandlingInvoked()
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

        command.parse(arrayOf("config"))

        val actualConfigurationServiceArgument = configurationServiceArgument
        assertThat(actualConfigurationServiceArgument)
            .isDefaultInstance()
    }

    @Test
    fun `When passed --config-dir, it should use override ConfigurationService`() {
        arrangeWithDatabases()
        val overrideConfigDir = temp.resolve("override-config-dir").apply {
            createDirectory()
        }
        // TODO: need an extraModule (DI) to override ConfigurationServiceFactory to capture argument

        command.parse(arrayOf(
            "--config-dir", "$overrideConfigDir",
            "config"
        ))
        val actualConfigurationServiceArgument = configurationServiceArgument

        assertThat(actualConfigurationServiceArgument, "service factory argument")
            .isOverrideInstance()
            .configDir()
            .isEqualTo(overrideConfigDir)
    }

    private fun arrangeWithDatabases() = runTest {
        coEvery { service.get() }.returns(Result.success(config))
        coEvery { service.listDatabasesByName() } answers { Result.success(dbConfigs.allByName) }
        coEvery { service.getDefaultDatabase() } returns(Result.success(dbConfigs.bar))
    }

    private fun arrangeWithoutDatabasesCase() {
        coEvery { service.get() }.returns(Result.success(Configuration.DEFAULT))
        coEvery { service.listDatabasesByName() } answers { Result.success(emptyMap()) }
        coEvery { service.getDefaultDatabase() } returns Result.success(null)
    }
}
