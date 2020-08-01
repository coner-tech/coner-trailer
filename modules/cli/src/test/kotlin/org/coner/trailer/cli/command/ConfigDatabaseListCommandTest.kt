package org.coner.trailer.cli.command

import assertk.all
import assertk.assertThat
import assertk.assertions.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.TestDatabaseConfigurations
import org.coner.trailer.cli.view.DatabaseConfigurationView
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.util.*

class ConfigDatabaseListCommandTest {

    lateinit var command: ConfigDatabaseListCommand

    @MockK
    lateinit var config: ConfigurationService
    @MockK
    lateinit var view: DatabaseConfigurationView

    @TempDir
    lateinit var temp: File

    lateinit var dbConfigs: TestDatabaseConfigurations
    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        dbConfigs = TestDatabaseConfigurations(temp)
        console = StringBufferConsole()
        command = ConfigDatabaseListCommand(console, view, config)
    }

    @Test
    fun `It should list databases`() {
        every { config.listDatabases() } returns dbConfigs.all
        val output = """
            foo => ${UUID.randomUUID()}
            bar => ${UUID.randomUUID()}
        """.trimIndent()
        every { view.render(dbConfigs.all) } returns(output)

        command.parse(emptyArray())

        verifySequence {
            config.listDatabases()
            view.render(dbConfigs.all)
        }
        confirmVerified(view, config)
        val actualOutput = console.output
        assertThat(actualOutput).isEqualTo(output)
    }
}