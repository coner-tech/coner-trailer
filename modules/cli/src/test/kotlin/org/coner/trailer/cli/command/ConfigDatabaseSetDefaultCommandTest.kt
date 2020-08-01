package org.coner.trailer.cli.command

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualToIgnoringGivenProperties
import assertk.assertions.isTrue
import assertk.assertions.prop
import com.github.ajalt.clikt.core.Abort
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.io.TestDatabaseConfigurations
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import java.io.File

class ConfigDatabaseSetDefaultCommandTest {

    lateinit var command: ConfigDatabaseSetDefaultCommand

    @MockK
    lateinit var config: ConfigurationService
    @MockK
    lateinit var noDatabase: DatabaseConfiguration

    @TempDir
    lateinit var temp: File

    lateinit var dbConfigs: TestDatabaseConfigurations

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        dbConfigs = TestDatabaseConfigurations(temp)
    }

    @Test
    fun `When given valid name it should set default`() {
        val slot = slot<DatabaseConfiguration>()
        every { config.configureDatabase(dbConfig = capture(slot)) }
        arrangeWithTestDatabaseConfigurations()

        command.parse(arrayOf("--name", "foo"))

        verifyOrder {
            config.listDatabasesByName()
            config.noDatabase
        }
        assertThat(slot.captured).all {
            isEqualToIgnoringGivenProperties(dbConfigs.foo)
            prop("default") { it.default }.isTrue()
        }
        confirmVerified(config)
    }

    @Test
    fun `When given invalid name it should abort`() {
        val baz = "baz"
        check(!dbConfigs.allByName.contains(baz)) // baz is not a valid dbConfig

        every { config.listDatabasesByName() } returns mapOf(
                noDatabase.name to noDatabase
        )
        every { config.noDatabase }
        command = ConfigDatabaseSetDefaultCommand(config)

        assertThrows<Abort> {
            command.parse(arrayOf("--name", baz))
        }

        verifyOrder {
            config.listDatabasesByName()
            config.noDatabase
        }
        confirmVerified(config)
    }
}

private fun ConfigDatabaseSetDefaultCommandTest.arrangeWithTestDatabaseConfigurations() {
    every { config.listDatabasesByName() } returns(dbConfigs.allByName)
    every { config.noDatabase } returns noDatabase
    command = ConfigDatabaseSetDefaultCommand(config)
}