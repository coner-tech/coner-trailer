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
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import java.nio.file.Path

class ConfigDatabaseRemoveCommandTest {

    lateinit var command: ConfigDatabaseRemoveCommand

    @MockK
    lateinit var service: ConfigurationService

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
        every { service.removeDatabase(any()) } answers { Unit }

        command.parse(arrayOf("--name", "foo"))

        verifyOrder {
            service.listDatabasesByName()
            service.noDatabase
            service.removeDatabase(dbConfigs.foo)
        }
        confirmVerified(service)
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
            service.listDatabasesByName()
        }
        confirmVerified(service)
    }

    @Test
    fun `When no databases exist it should still print its help`() {
        every { service.listDatabasesByName() } returns mapOf(
                dbConfigs.noDatabase.name to dbConfigs.noDatabase
        )

        assertThrows<PrintHelpMessage> {
            command.parse(arrayOf("--help"))
        }

        verify { service.listDatabasesByName() }
        confirmVerified(service)
    }

    @Test
    fun `When no databases exist it should not remove anything`() {
        every { service.listDatabasesByName() } returns mapOf(
                dbConfigs.noDatabase.name to dbConfigs.noDatabase
        )
        every { service.noDatabase } returns dbConfigs.noDatabase

        assertThrows<Abort> {
            command.parse(arrayOf("--name", dbConfigs.noDatabase.name))
        }

        verifyOrder {
            service.listDatabasesByName()
            service.noDatabase
        }
        confirmVerified(service)
    }
}


private fun ConfigDatabaseRemoveCommandTest.arrangeWithTestDatabaseConfigurations() {
    every { service.listDatabasesByName() }.returns(dbConfigs.allByName)
    every { service.noDatabase }.returns(dbConfigs.noDatabase)
    command = ConfigDatabaseRemoveCommand(
        di = DI {
            bind<ConfigurationService>() with instance(service)
        }
    )
}