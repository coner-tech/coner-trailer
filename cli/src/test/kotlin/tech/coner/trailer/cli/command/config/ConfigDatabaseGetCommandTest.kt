package tech.coner.trailer.cli.command.config

import assertk.assertThat
import assertk.assertions.contains
import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.context
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import io.mockk.verifyOrder
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.di.ConfigurationServiceArgument
import tech.coner.trailer.io.ConfigurationService
import tech.coner.trailer.io.TestDatabaseConfigurations
import tech.coner.trailer.io.TestEnvironments
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindFactory
import org.kodein.di.bindInstance
import java.nio.file.Path
import java.util.*

@ExtendWith(MockKExtension::class)
class ConfigDatabaseGetCommandTest : DIAware {

    lateinit var command: ConfigDatabaseGetCommand

    override val di = DI.lazy {
        bindFactory { _: ConfigurationServiceArgument -> service }
        bindInstance { view }
    }

    @MockK lateinit var service: ConfigurationService
    @MockK lateinit var view: DatabaseConfigurationView

    @TempDir lateinit var root: Path

    lateinit var dbConfigs: TestDatabaseConfigurations
    lateinit var global: GlobalModel
    lateinit var testConsole: StringBufferConsole

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
        dbConfigs = TestDatabaseConfigurations(root)
        command = ConfigDatabaseGetCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `When given valid name option it should get it`() {
        arrangeWithTestDatabaseConfigurations()
        val dbConfig = dbConfigs.foo
        global.environment = TestEnvironments.temporary(di, root, dbConfig)
        val render = "view.render(foo) => ${UUID.randomUUID()}"
        every { view.render(dbConfigs.foo) }.returns(render)

        command.parse(arrayOf("foo"))

        verifyOrder {
            service.listDatabasesByName()
            view.render(dbConfigs.foo)
        }
        confirmVerified(service, view)
        assertThat(testConsole.output.contains(render))
    }

    @Test
    fun `When given invalid name option it should fail`() {
        val baz = "baz"
        check(!dbConfigs.allByName.contains(baz)) // baz is not a valid dbConfig
        global.environment = TestEnvironments.minimal(di)

        arrangeWithTestDatabaseConfigurations()

        assertThrows<Abort> {
            command.parse(arrayOf(baz))
        }

        verify { service.listDatabasesByName() }
        confirmVerified(service, view)
        assertThat(testConsole.output).contains("No database found with name")
    }
}

private fun ConfigDatabaseGetCommandTest.arrangeWithTestDatabaseConfigurations() {
    every { service.listDatabasesByName() }.returns(dbConfigs.allByName)

}