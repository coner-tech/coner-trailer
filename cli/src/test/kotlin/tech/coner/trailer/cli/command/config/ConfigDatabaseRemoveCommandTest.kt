package tech.coner.trailer.cli.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEmpty
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.core.context
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.clikt.error
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.di.mockkIoModule
import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.TestConfigurations
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.ConfigurationService
import java.nio.file.Path

@ExtendWith(MockKExtension::class)
class ConfigDatabaseRemoveCommandTest : DIAware {

    lateinit var command: ConfigDatabaseRemoveCommand

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkIoModule)
    }
    override val diContext = diContext { global.requireEnvironment() }

    @TempDir lateinit var root: Path

    private val service: ConfigurationService by instance()
    @MockK lateinit var view: DatabaseConfigurationView

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel
    lateinit var config: Configuration
    lateinit var dbConfig: DatabaseConfiguration

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        val configs = TestConfigurations(root)
        config = configs.testConfiguration()
        val dbConfigs = configs.testDatabaseConfigurations
        dbConfig = dbConfigs.foo
        global = GlobalModel(
            environment = TestEnvironments.temporary(di, root, config, dbConfigs.bar)
        )
        command = ConfigDatabaseRemoveCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `When given valid name option it should remove named database`() {
        val newConfig = config.copy(
            databases = emptyMap()
        )
        coEvery { service.removeDatabase(any()) } returns Result.success(newConfig)

        command.parse(arrayOf(dbConfig.name))

        coVerifySequence {
            service.removeDatabase(dbConfig.name)
        }
        assertThat(testConsole).all {
            output().isEmpty()
            error().isEmpty()
        }
    }

    @Test
    fun `When given invalid name option it should fail`() {
        val exception = Exception("No database found with name")
        coEvery { service.removeDatabase(any()) } returns Result.failure(exception)

        assertThrows<ProgramResult> {
            command.parse(arrayOf("baz"))
        }

        coVerifySequence {
            service.removeDatabase("baz")
        }
        assertThat(testConsole).all {
            error().all {
                contains("Failed to remove database")
                contains(exception.message!!)
            }
            output().isEmpty()
        }
    }
}
