package tech.coner.trailer.cli.command.config

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.di.mockkIoModule
import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.TestConfigurations
import tech.coner.trailer.io.TestDatabaseConfigurations
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.ConfigurationService
import java.nio.file.Path
import java.util.*

@ExtendWith(MockKExtension::class)
class ConfigDatabaseListCommandTest : DIAware {

    lateinit var command: ConfigDatabaseListCommand

    override val di = DI.lazy {
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
        every { service.listDatabases() } returns dbConfigs.all
        val output = """
            foo => ${UUID.randomUUID()}
            bar => ${UUID.randomUUID()}
        """.trimIndent()
        every { view.render(dbConfigs.all) } returns(output)

        command.parse(emptyArray())

        verifySequence {
            service.listDatabases()
            view.render(dbConfigs.all)
        }
        assertThat(testConsole).output().isEqualTo(output)
    }
}