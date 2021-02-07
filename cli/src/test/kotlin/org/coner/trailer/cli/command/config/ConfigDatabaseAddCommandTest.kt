package org.coner.trailer.cli.command.config

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.io.TestDatabaseConfigurations
import org.coner.trailer.cli.view.DatabaseConfigurationView
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import java.nio.file.Path
import java.util.*

@ExtendWith(MockKExtension::class)
class ConfigDatabaseAddCommandTest {

    lateinit var command: ConfigDatabaseAddCommand

    @MockK
    lateinit var view: DatabaseConfigurationView
    @MockK
    lateinit var service: ConfigurationService

    @TempDir
    lateinit var temp: Path

    lateinit var dbConfigs: TestDatabaseConfigurations
    lateinit var testConsole: StringBufferConsole

    @BeforeEach
    fun before() {
        dbConfigs = TestDatabaseConfigurations(temp)
        testConsole = StringBufferConsole()
        command = ConfigDatabaseAddCommand(
            di = DI {
                bind<DatabaseConfigurationView>() with instance(view)
                bind<ConfigurationService>() with instance(service)
            }
        ).context {
            console = testConsole
        }
    }

    @Test
    fun `When given with all arguments it should configure and display`() {
        val configSlot = slot<DatabaseConfiguration>()
        justRun { service.configureDatabase(capture(configSlot)) }
        val render = "bar => ${UUID.randomUUID()}"
        val bar = dbConfigs.bar
        val viewSlot = slot<DatabaseConfiguration>()
        every { view.render(capture(viewSlot)) } returns render

        command.parse(arrayOf(
                "--name", bar.name,
                "--crispy-fish-database", bar.crispyFishDatabase.toString(),
                "--snoozle-database", bar.snoozleDatabase.toString(),
                "--motorsportreg-username", "${bar.motorsportReg?.username}",
                "--motorsportreg-organization-id", "${bar.motorsportReg?.organizationId}",
                "--default"
        ))

        verifySequence {
            service.configureDatabase(eq(bar))
            view.render(eq(bar))
        }
        val actualDbConfig = configSlot.captured
        assertThat(actualDbConfig).isEqualTo(bar)
        assertThat(testConsole.output, "console output").isEqualTo(render)
    }
}