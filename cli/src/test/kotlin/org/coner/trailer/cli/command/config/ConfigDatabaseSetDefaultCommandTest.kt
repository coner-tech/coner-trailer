package org.coner.trailer.cli.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualToIgnoringGivenProperties
import assertk.assertions.isTrue
import assertk.assertions.prop
import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verifySequence
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.di.EnvironmentScope
import org.coner.trailer.io.ConfigurationService
import org.coner.trailer.io.DatabaseConfiguration
import org.coner.trailer.io.TestDatabaseConfigurations
import org.coner.trailer.io.TestEnvironments
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import java.nio.file.Path

@ExtendWith(MockKExtension::class)
class ConfigDatabaseSetDefaultCommandTest : DIAware {

    lateinit var command: ConfigDatabaseSetDefaultCommand

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