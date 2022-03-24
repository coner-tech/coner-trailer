package tech.coner.trailer.cli.command.config

import assertk.assertThat
import assertk.assertions.contains
import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verifySequence
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.di.EnvironmentScope
import tech.coner.trailer.io.service.ConfigurationService
import tech.coner.trailer.io.TestDatabaseConfigurations
import tech.coner.trailer.io.TestEnvironments
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import java.nio.file.Path

@ExtendWith(MockKExtension::class)
class ConfigDatabaseRemoveCommandTest : DIAware {

    lateinit var command: ConfigDatabaseRemoveCommand

    override val di = DI.lazy {
        bind { scoped(EnvironmentScope).singleton { service } }
    }

    @MockK lateinit var service: ConfigurationService

    @TempDir lateinit var temp: Path

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel
    lateinit var dbConfigs: TestDatabaseConfigurations

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        dbConfigs = TestDatabaseConfigurations(temp)
        global = GlobalModel()
            .apply { environment = TestEnvironments.minimal(di) }
        command = ConfigDatabaseRemoveCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `When given valid name option it should remove named database`() {
        arrangeWithTestDatabaseConfigurations()
        justRun { service.removeDatabase(any()) }

        command.parse(arrayOf("foo"))

        verifySequence {
            service.listDatabasesByName()
            service.removeDatabase(dbConfigs.foo)
        }
    }

    @Test
    fun `When given invalid name option it should fail`() {
        val baz = "baz"
        check(!dbConfigs.allByName.contains(baz)) // baz is not a valid dbConfig
        arrangeWithTestDatabaseConfigurations()

        assertThrows<Abort> {
            command.parse(arrayOf("baz"))
        }

        verifySequence {
            service.listDatabasesByName()
        }
        assertThat(testConsole.output, "console output").contains("No database found with name")
    }
}


private fun ConfigDatabaseRemoveCommandTest.arrangeWithTestDatabaseConfigurations() {
    every { service.listDatabasesByName() }.returns(dbConfigs.allByName)
}