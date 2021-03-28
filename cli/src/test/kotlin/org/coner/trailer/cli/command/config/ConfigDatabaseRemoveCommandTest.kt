package org.coner.trailer.cli.command.config

import assertk.assertThat
import assertk.assertions.contains
import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.context
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.io.ConfigurationService
import org.coner.trailer.cli.io.TestDatabaseConfigurations
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import java.nio.file.Path

@ExtendWith(MockKExtension::class)
class ConfigDatabaseRemoveCommandTest {

    lateinit var command: ConfigDatabaseRemoveCommand

    @MockK
    lateinit var service: ConfigurationService

    lateinit var testConsole: StringBufferConsole

    @TempDir
    lateinit var temp: Path

    lateinit var dbConfigs: org.coner.trailer.cli.io.TestDatabaseConfigurations


    @BeforeEach
    fun before() {
        dbConfigs = org.coner.trailer.cli.io.TestDatabaseConfigurations(temp)
        testConsole = StringBufferConsole()
        command = ConfigDatabaseRemoveCommand(
            di = DI {
                bind<ConfigurationService>() with instance(service)
            }
        ).context {
            console = testConsole
        }
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
    every { service.noDatabase }.returns(dbConfigs.noDatabase)
}