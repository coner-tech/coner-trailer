package tech.coner.trailer.cli.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualToIgnoringGivenProperties
import assertk.assertions.isTrue
import assertk.assertions.prop
import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.DI
import org.kodein.di.DIAware
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.di.mockkIoModule
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.TestDatabaseConfigurations
import tech.coner.trailer.io.TestEnvironments
import java.nio.file.Path

@ExtendWith(MockKExtension::class)
class ConfigDatabaseSetDefaultCommandTest : DIAware {

    lateinit var command: ConfigDatabaseSetDefaultCommand

    override val di = DI.lazy {
        import(mockkIoModule)
    }

    @TempDir lateinit var root: Path

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel
    lateinit var dbConfigs: TestDatabaseConfigurations

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        dbConfigs = TestDatabaseConfigurations(root)
        global = GlobalModel(environment = TestEnvironments.temporary(di, root, dbConfigs.foo))
        command = ConfigDatabaseSetDefaultCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `When given valid name it should set default`() {
        arrangeWithTestDatabaseConfigurations()
        val slot = slot<DatabaseConfiguration>()
        justRun { service.configureDatabase(capture(slot)) }

        command.parse(arrayOf("foo"))

        verifySequence {
            service.listDatabasesByName()
            service.configureDatabase(any())
        }
        assertThat(slot.captured, "database configured as default").all {
            isEqualToIgnoringGivenProperties(dbConfigs.foo, DatabaseConfiguration::default)
            prop("default") { it.default }.isTrue()
        }
    }

    @Test
    fun `When given invalid name it should fail`() {
        val baz = "baz"
        check(!dbConfigs.allByName.contains(baz)) // baz is not a valid dbConfig
        every { service.listDatabasesByName() } returns mapOf(
                dbConfigs.noDatabase.name to dbConfigs.noDatabase
        )

        assertThrows<Abort> {
            command.parse(arrayOf(baz))
        }

        verifySequence {
            service.listDatabasesByName()
        }
    }
}

private fun ConfigDatabaseSetDefaultCommandTest.arrangeWithTestDatabaseConfigurations() {
    every { service.listDatabasesByName() } returns(dbConfigs.allByName)
}