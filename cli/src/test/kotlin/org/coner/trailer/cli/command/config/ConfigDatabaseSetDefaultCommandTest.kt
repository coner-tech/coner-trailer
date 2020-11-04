package org.coner.trailer.cli.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualToIgnoringGivenProperties
import assertk.assertions.isTrue
import assertk.assertions.prop
import com.github.ajalt.clikt.core.BadParameterValue
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.io.TestDatabaseConfigurations
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

class ConfigDatabaseSetDefaultCommandTest {

    lateinit var command: ConfigDatabaseSetDefaultCommand

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
    fun `When given valid name it should set default`() {
        arrangeWithTestDatabaseConfigurations()
        val slot = slot<DatabaseConfiguration>()
        every { config.configureDatabase(capture(slot)) } answers { Unit }

        command.parse(arrayOf("--name", "foo"))

        verifyOrder {
            config.listDatabasesByName()
            config.noDatabase
            config.configureDatabase(any())
        }
        confirmVerified(config)
        assertThat(slot.captured).all {
            isEqualToIgnoringGivenProperties(dbConfigs.foo, DatabaseConfiguration::default)
            prop("default") { it.default }.isTrue()
        }
    }

    @Test
    fun `When given invalid name it should fail`() {
        val baz = "baz"
        check(!dbConfigs.allByName.contains(baz)) // baz is not a valid dbConfig

        every { config.listDatabasesByName() } returns mapOf(
                dbConfigs.noDatabase.name to dbConfigs.noDatabase
        )
        command = ConfigDatabaseSetDefaultCommand(config)

        assertThrows<BadParameterValue> {
            command.parse(arrayOf("--name", baz))
        }

        verifyOrder {
            config.listDatabasesByName()
        }
        confirmVerified(config)
    }
}

private fun ConfigDatabaseSetDefaultCommandTest.arrangeWithTestDatabaseConfigurations() {
    every { config.listDatabasesByName() } returns(dbConfigs.allByName)
    every { config.noDatabase } returns(dbConfigs.noDatabase)
    command = ConfigDatabaseSetDefaultCommand(config)
}