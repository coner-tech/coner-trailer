package tech.coner.trailer.cli.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.core.context
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.clikt.error
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.di.mockkIoModule
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.TestConfigurations
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.payload.ConfigAddDatabaseOutcome
import tech.coner.trailer.io.payload.ConfigAddDatabaseParam
import tech.coner.trailer.io.service.ConfigurationService
import java.nio.file.Path
import java.util.*

@ExtendWith(MockKExtension::class)
class ConfigDatabaseAddCommandTest : DIAware {

    lateinit var command: ConfigDatabaseAddCommand

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkIoModule)
        bindInstance { view }
    }
    override val diContext = diContext { global.requireEnvironment() }

    private val service: ConfigurationService by instance()
    @MockK lateinit var view: DatabaseConfigurationView

    @TempDir lateinit var root: Path

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel
    lateinit var dbConfig: DatabaseConfiguration

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        val configs = TestConfigurations(root)
        dbConfig = configs.testDatabaseConfigurations.bar
        global = GlobalModel(
            environment = TestEnvironments.temporary(di, root, configs.testConfiguration(), dbConfig)
        )
        command = ConfigDatabaseAddCommand(di, global)
            .context { console = testConsole }
    }

    @Test
    fun `When given with valid arguments it should add database and display`() {
        val expectedParam = ConfigAddDatabaseParam(
            name = dbConfig.name,
            crispyFishDatabase = dbConfig.crispyFishDatabase,
            snoozleDatabase = dbConfig.snoozleDatabase,
            motorsportReg = ConfigAddDatabaseParam.MotorsportReg(
                username = dbConfig.motorsportReg?.username,
                organizationId = dbConfig.motorsportReg?.organizationId
            ),
            default = dbConfig.default
        )
        every {
            service.addDatabase(any())
        } returns Result.success(ConfigAddDatabaseOutcome(
            configuration = global.requireEnvironment().requireConfiguration(),
            addedDbConfig = dbConfig
        ))
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
            service.addDatabase(expectedParam)
            view.render(dbConfig)
        }
        confirmVerified(service, view)
        assertThat(testConsole).output().isEqualTo(render)
    }

    @Test
    fun `When it fails to add database, it should display error and exit`() {
        val exception = Exception("Something went wrong")
        every {
            service.addDatabase(any())
        } returns Result.failure(exception)

        assertThrows<ProgramResult> {
            command.parse(arrayOf(
                "--name", dbConfig.name,
                "--crispy-fish-database", dbConfig.crispyFishDatabase.toString(),
                "--snoozle-database", dbConfig.snoozleDatabase.toString(),
                "--motorsportreg-username", "${dbConfig.motorsportReg?.username}",
                "--motorsportreg-organization-id", "${dbConfig.motorsportReg?.organizationId}",
                "--default"
            ))
        }

        verifySequence {
            service.addDatabase(any())
        }
        confirmVerified(service, view)
        assertThat(testConsole).error()
            .all {
                contains("Failed to add database")
                contains(exception.message!!)
            }
    }
}