package org.coner.trailer.cli.command.config

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verifySequence
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.io.TestDatabaseConfigurations
import org.coner.trailer.cli.view.DatabaseConfigurationView
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.util.*

class ConfigDatabaseSetCommandTest {

    lateinit var command: ConfigDatabaseSetCommand

    @MockK
    lateinit var view: DatabaseConfigurationView
    @MockK
    lateinit var config: ConfigurationService

    @TempDir
    lateinit var temp: File

    lateinit var dbConfigs: TestDatabaseConfigurations
    lateinit var console: StringBufferConsole

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        dbConfigs = TestDatabaseConfigurations(temp)
        console = StringBufferConsole()
        command = ConfigDatabaseSetCommand(console, view, config)
    }

    @Test
    fun `When given with all arguments it should configure and display`() {
        val configSlot = slot<DatabaseConfiguration>()
        every { config.configureDatabase(capture(configSlot)) } answers { Unit }
        val render = "bar => ${UUID.randomUUID()}"
        val bar = dbConfigs.bar
        val viewSlot = slot<DatabaseConfiguration>()
        every { view.render(capture(viewSlot)) } returns render

        command.parse(arrayOf(
                "--name", bar.name,
                "--crispy-fish-database", bar.crispyFishDatabase.toString(),
                "--snoozle-database", bar.snoozleDatabase.toString(),
                "--default"
        ))

        verifySequence {
            config.configureDatabase(eq(bar))
            view.render(eq(bar))
        }
        val actualOutput = console.output
        val actualDbConfig = configSlot.captured
        assertThat(actualDbConfig).isEqualTo(bar)
        assertThat(actualOutput).isEqualTo(render)
    }
}