package org.coner.trailer.cli.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualToIgnoringGivenProperties
import assertk.assertions.isTrue
import assertk.assertions.prop
import com.github.ajalt.clikt.core.Abort
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.io.ConfigurationService
import org.coner.trailer.io.DatabaseConfiguration
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
class ConfigDatabaseSetDefaultCommandTest {

    lateinit var command: ConfigDatabaseSetDefaultCommand

    @MockK
    lateinit var service: ConfigurationService

    @TempDir
    lateinit var temp: Path

    lateinit var dbConfigs: org.coner.trailer.cli.io.TestDatabaseConfigurations

    @BeforeEach
    fun before() {
        dbConfigs = org.coner.trailer.cli.io.TestDatabaseConfigurations(temp)
        command = ConfigDatabaseSetDefaultCommand(
            di = DI {
                bind<ConfigurationService>() with instance(service)
            }
        )
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
    every { service.noDatabase } returns(dbConfigs.noDatabase)
}