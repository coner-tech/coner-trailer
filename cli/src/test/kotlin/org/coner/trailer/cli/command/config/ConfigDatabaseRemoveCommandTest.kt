package org.coner.trailer.cli.command.config

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.BadParameterValue
import com.github.ajalt.clikt.core.PrintHelpMessage
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.TestDatabaseConfigurations
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

class ConfigDatabaseRemoveCommandTest {

    lateinit var command: ConfigDatabaseRemoveCommand

    @MockK
    lateinit var config: ConfigurationService

    @TempDir
    lateinit var temp: Path

    lateinit var dbConfigs: TestDatabaseConfigurations

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        dbConfigs = TestDatabaseConfigurations(temp)
    }

    @Test
    fun `When given valid name option it should remove named database`() {
        arrangeWithTestDatabaseConfigurations()
        every { config.removeDatabase(any()) } answers { Unit }

        command.parse(arrayOf("--name", "foo"))

        verifyOrder {
            config.listDatabasesByName()
            config.noDatabase
            config.removeDatabase(dbConfigs.foo)
        }
        confirmVerified(config)
    }

    @Test
    fun `When given invalid name option it should fail`() {
        val baz = "baz"
        check(!dbConfigs.allByName.contains(baz)) // baz is not a valid dbConfig

        arrangeWithTestDatabaseConfigurations()

        assertThrows<BadParameterValue> {
            command.parse(arrayOf("--name", "baz"))
        }
        verify {
            config.listDatabasesByName()
        }
        confirmVerified(config)
    }

    @Test
    fun `When no databases exist it should still print its help`() {
        every { config.listDatabasesByName() } returns mapOf(
                dbConfigs.noDatabase.name to dbConfigs.noDatabase
        )
        command = ConfigDatabaseRemoveCommand(config)

        assertThrows<PrintHelpMessage> {
            command.parse(arrayOf("--help"))
        }

        verify { config.listDatabasesByName() }
        confirmVerified(config)
    }

    @Test
    fun `When no databases exist it should not remove anything`() {
        every { config.listDatabasesByName() } returns mapOf(
                dbConfigs.noDatabase.name to dbConfigs.noDatabase
        )
        every { config.noDatabase } returns dbConfigs.noDatabase
        command = ConfigDatabaseRemoveCommand(config)

        assertThrows<Abort> {
            command.parse(arrayOf("--name", dbConfigs.noDatabase.name))
        }

        verifyOrder {
            config.listDatabasesByName()
            config.noDatabase
        }
        confirmVerified(config)
    }
}


private fun ConfigDatabaseRemoveCommandTest.arrangeWithTestDatabaseConfigurations() {
    every { config.listDatabasesByName() }.returns(dbConfigs.allByName)
    every { config.noDatabase }.returns(dbConfigs.noDatabase)
    command = ConfigDatabaseRemoveCommand(config)
}