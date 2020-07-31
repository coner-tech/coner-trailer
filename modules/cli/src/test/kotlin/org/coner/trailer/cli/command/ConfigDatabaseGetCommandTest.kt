package org.coner.trailer.cli.command

import com.github.ajalt.clikt.core.BadParameterValue
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.TestDatabaseConfigurations
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import java.io.File

class ConfigDatabaseGetCommandTest {

    lateinit var command: ConfigDatabaseGetCommand

    @RelaxedMockK
    lateinit var config: ConfigurationService

    @TempDir
    lateinit var temp: File

    lateinit var dbConfigs: TestDatabaseConfigurations

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        dbConfigs = TestDatabaseConfigurations(temp)
    }

    @Test
    fun `When given valid name option it should get it`() {
        arrangeWithTestDatabaseConfigurations()

        command.parse(arrayOf("--name", "foo"))
    }

    @Test
    fun `When given invalid name option it should fail`() {
        val baz = "baz"
        check(!dbConfigs.allByName.contains(baz)) // baz is not a valid dbConfig

        arrangeWithTestDatabaseConfigurations()

        assertThrows<BadParameterValue> {
            command.parse(arrayOf("--name", baz))
        }
    }
}

fun ConfigDatabaseGetCommandTest.arrangeWithTestDatabaseConfigurations() {
    every { config.listDatabasesByName() }.returns(dbConfigs.allByName)
    command = ConfigDatabaseGetCommand(config)
}