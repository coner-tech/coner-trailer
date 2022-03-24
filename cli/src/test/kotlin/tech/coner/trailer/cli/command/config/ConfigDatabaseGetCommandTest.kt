package tech.coner.trailer.cli.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEmpty
import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.core.context
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifyOrder
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
import tech.coner.trailer.cli.di.mockkServiceModule
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.di.ConfigurationServiceFactory
import tech.coner.trailer.di.mockkIoModule
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.TestConfigurations
import tech.coner.trailer.io.TestDatabaseConfigurations
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.service.ConfigurationService
import tech.coner.trailer.io.service.NotFoundException
import java.nio.file.Path
import java.util.*

@ExtendWith(MockKExtension::class)
class ConfigDatabaseGetCommandTest : DIAware {

    lateinit var command: ConfigDatabaseGetCommand

    override val di = DI.lazy {
        import(mockkIoModule)
        bindInstance { view }
    }
    override val diContext = diContext { global.requireEnvironment() }

    private val service: ConfigurationService by instance()
    @MockK lateinit var view: DatabaseConfigurationView

    @TempDir lateinit var root: Path

    lateinit var dbConfigs: TestDatabaseConfigurations
    lateinit var global: GlobalModel
    lateinit var testConsole: StringBufferConsole

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        val configs = TestConfigurations(root)
        dbConfigs = configs.testDatabaseConfigurations
        global = GlobalModel(
            environment = TestEnvironments.temporary(di, root, configs.testConfiguration(), dbConfigs.foo)
        )
        command = ConfigDatabaseGetCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `When given valid name option it should get it`() {
        val render = "view.render(foo) => ${UUID.randomUUID()}"
        val dbConfig = dbConfigs.foo
        every { service.findDatabaseByName(any()) } returns Result.success(dbConfig)
        every { view.render(any<DatabaseConfiguration>()) }.returns(render)

        command.parse(arrayOf(dbConfig.name))

        verifySequence {
            service.findDatabaseByName(dbConfig.name)
            view.render(dbConfigs.foo)
        }
        assertThat(testConsole).output().contains(render)
    }

    @Test
    fun `When given invalid name option it should fail`() {
        val baz = "baz"
        check(!dbConfigs.allByName.contains(baz)) { "Failed prerequisite: test database configs expected not to contain baz database"}
        every { service.findDatabaseByName(any()) } returns Result.failure(NotFoundException("nfe"))

        assertThrows<ProgramResult> {
            command.parse(arrayOf(baz))
        }

        verifySequence {
            service.findDatabaseByName(baz)
        }
        confirmVerified(service, view)
        assertThat(testConsole).error().all {
            contains("Failed to find database by name")
            contains("nfe")
        }
        assertThat(testConsole).output().isEmpty()
    }
}
