package tech.coner.trailer.cli.command.config

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import java.nio.file.Path
import java.util.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.di.mockkIoModule
import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.TestConfigurations
import tech.coner.trailer.io.TestDatabaseConfigurations
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.ConfigurationService

@ExtendWith(MockKExtension::class)
class ConfigDatabaseListCommandTest : DIAware {

    lateinit var command: ConfigDatabaseListCommand

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkIoModule)
        bindInstance { view }
    }
    override val diContext = diContext { global.requireEnvironment() }

    private val service: ConfigurationService by instance()
    @MockK lateinit var view: DatabaseConfigurationView

    @TempDir lateinit var root: Path

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel
    lateinit var config: Configuration
    lateinit var dbConfigs: TestDatabaseConfigurations

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        val configs = TestConfigurations(root)
        config = configs.testConfiguration()
        dbConfigs = configs.testDatabaseConfigurations
        global = GlobalModel(
            environment = TestEnvironments.temporary(di, root, config, dbConfigs.bar)
        )
        command = ConfigDatabaseListCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `It should list databases`() {
        coEvery { service.listDatabases() } returns Result.success(dbConfigs.all)
        val output = """
            foo => ${UUID.randomUUID()}
            bar => ${UUID.randomUUID()}
        """.trimIndent()
        every { view.render(dbConfigs.all) } returns(output)

        command.parse(emptyArray())

        coVerifySequence {
            service.listDatabases()
            view.render(dbConfigs.all)
        }
        assertThat(testConsole).output().isEqualTo(output)
    }
}