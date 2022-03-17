package tech.coner.trailer.cli.command.config

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verifySequence
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.di.ConfigurationServiceArgument
import tech.coner.trailer.io.ConfigurationService
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.TestDatabaseConfigurations
import tech.coner.trailer.io.TestEnvironments
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindFactory
import org.kodein.di.bindInstance
import java.nio.file.Path
import java.util.*

@ExtendWith(MockKExtension::class)
class ConfigDatabaseAddCommandTest : DIAware {

    lateinit var command: ConfigDatabaseAddCommand

    override val di = DI.lazy {
        bindInstance { view }
        bindFactory { _: ConfigurationServiceArgument -> service }
    }

    @MockK lateinit var view: DatabaseConfigurationView
    @MockK lateinit var service: ConfigurationService

    @TempDir lateinit var root: Path

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel
    lateinit var dbConfig: DatabaseConfiguration

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        dbConfig = TestDatabaseConfigurations(root).bar
        global = GlobalModel()
            .apply { environment = TestEnvironments.temporary(di, root, dbConfig) }
        command = ConfigDatabaseAddCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `When given with all arguments it should configure and display`() {
        val configSlot = slot<DatabaseConfiguration>()
        justRun { service.configureDatabase(capture(configSlot)) }
        val render = "bar => ${UUID.randomUUID()}"
        val viewSlot = slot<DatabaseConfiguration>()
        every { view.render(capture(viewSlot)) } returns render

        command.parse(arrayOf(
            "--name", dbConfig.name,
            "--crispy-fish-database", dbConfig.crispyFishDatabase.toString(),
            "--snoozle-database", dbConfig.snoozleDatabase.toString(),
            "--motorsportreg-username", "${dbConfig.motorsportReg?.username}",
            "--motorsportreg-organization-id", "${dbConfig.motorsportReg?.organizationId}",
            "--default"
        ))

        verifySequence {
            service.configureDatabase(eq(dbConfig))
            view.render(eq(dbConfig))
        }
        val actualDbConfig = configSlot.captured
        assertThat(actualDbConfig).isEqualTo(dbConfig)
        assertThat(testConsole.output, "console output").isEqualTo(render)
    }
}