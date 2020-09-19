package org.coner.trailer.cli.command.config

import assertk.assertThat
import com.github.ajalt.clikt.core.BadParameterValue
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.TestDatabaseConfigurations
import org.coner.trailer.cli.view.DatabaseConfigurationView
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.util.*

class ConfigDatabaseGetCommandTest {

    lateinit var command: ConfigDatabaseGetCommand

    @MockK
    lateinit var config: ConfigurationService
    @MockK
    lateinit var view: DatabaseConfigurationView

    @TempDir
    lateinit var temp: File

    lateinit var console: StringBufferConsole
    lateinit var dbConfigs: TestDatabaseConfigurations

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        console = StringBufferConsole()
        dbConfigs = TestDatabaseConfigurations(temp)
    }

    @Test
    fun `When given valid name option it should get it`() {
        arrangeWithTestDatabaseConfigurations()
        val render = "view.render(foo) => ${UUID.randomUUID()}"
        every { view.render(dbConfigs.foo) }.returns(render)

        command.parse(arrayOf("--name", "foo"))

        verifyOrder {
            config.listDatabasesByName()
            view.render(dbConfigs.foo)
        }
        confirmVerified(config, view)
        assertThat(console.output.contains(render))
    }

    @Test
    fun `When given invalid name option it should fail`() {
        val baz = "baz"
        check(!dbConfigs.allByName.contains(baz)) // baz is not a valid dbConfig

        arrangeWithTestDatabaseConfigurations()

        assertThrows<BadParameterValue> {
            command.parse(arrayOf("--name", baz))
        }

        verify { config.listDatabasesByName() }
        confirmVerified(config, view)
    }
}

private fun ConfigDatabaseGetCommandTest.arrangeWithTestDatabaseConfigurations() {
    every { config.listDatabasesByName() }.returns(dbConfigs.allByName)
    command = ConfigDatabaseGetCommand(
            useConsole = console,
            config = config,
            view = view
    )
}