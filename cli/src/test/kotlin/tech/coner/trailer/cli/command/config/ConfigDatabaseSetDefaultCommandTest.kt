package tech.coner.trailer.cli.command.config

import assertk.all
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.clikt.error
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.di.mockkIoModule
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.TestConfigurations
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.payload.ConfigSetDefaultDatabaseOutcome
import tech.coner.trailer.io.service.ConfigurationService
import tech.coner.trailer.io.service.NotFoundException
import java.nio.file.Path

@ExtendWith(MockKExtension::class)
class ConfigDatabaseSetDefaultCommandTest : DIAware {

    lateinit var command: ConfigDatabaseSetDefaultCommand

    override val di = DI.lazy {
        import(mockkIoModule)
        bindInstance { view }
    }
    override val diContext = diContext { global.requireEnvironment() }

    @TempDir lateinit var root: Path

    private val service: ConfigurationService by instance()
    @MockK lateinit var view: DatabaseConfigurationView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel
    lateinit var testConfigs: TestConfigurations

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        testConfigs = TestConfigurations(root)
        global = GlobalModel(environment = TestEnvironments.temporary(di, root, testConfigs.testConfiguration(), testConfigs.testDatabaseConfigurations.foo))
        command = ConfigDatabaseSetDefaultCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `When given valid name it should set default and succeed`() {
        val slot = slot<String>()
        val originalConfig = global.requireEnvironment().requireConfiguration()
        val originalFooDbConfig = global.requireEnvironment().requireDatabaseConfiguration()
        check(originalFooDbConfig.name == "foo") { "Prerequisite failed: expected database named foo"}
        check(!originalFooDbConfig.default) { "Prerequisite failed: expected foo database not default"}
        val expectedDbConfig = originalFooDbConfig.copy(
            default = true
        )
        val expectedConfig = originalConfig.copy(
            databases = originalConfig.databases.toMutableMap().apply { put(expectedDbConfig.name, expectedDbConfig) },
            defaultDatabaseName = expectedDbConfig.name
        )
        every {
            service.setDefaultDatabase(capture(slot))
        } returns Result.success(ConfigSetDefaultDatabaseOutcome(expectedConfig, expectedDbConfig))
        val viewRender = "view render"
        every { view.render(any<DatabaseConfiguration>()) } returns viewRender

        command.parse(arrayOf(originalFooDbConfig.name))

        verifySequence {
            service.setDefaultDatabase(originalFooDbConfig.name)
            view.render(expectedDbConfig)
        }
        assertAll {
            assertThat(slot.captured, "database name passed").isEqualTo(expectedDbConfig.name)
            assertThat(testConsole).output().isEqualTo(viewRender)
        }
    }

    @Test
    fun `When given invalid name it should fail`() {
        val exception = NotFoundException("Not found")
        every { service.setDefaultDatabase(any()) } returns Result.failure(exception)
        val name = "irrelevant"

        val actual = assertThrows<ProgramResult> {
            command.parse(arrayOf(name))
        }

        verifySequence { service.setDefaultDatabase(name) }
        verifySequence(inverse = true) { view.render(any<DatabaseConfiguration>()) }
        assertThat(testConsole)
            .error()
            .all {
                contains("Failed to set default database")
                contains(exception.message!!)
            }
    }
}
