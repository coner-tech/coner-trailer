package tech.coner.trailer.cli.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.ProgramResult
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.kodein.di.DirectDI
import org.kodein.di.instance
import org.kodein.di.on
import tech.coner.trailer.cli.clikt.error
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.view.DatabaseConfigurationView
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.payload.ConfigAddDatabaseOutcome
import tech.coner.trailer.io.payload.ConfigAddDatabaseParam
import tech.coner.trailer.io.service.ConfigurationService
import java.util.*

class ConfigDatabaseAddCommandTest : BaseConfigCommandTest<ConfigDatabaseAddCommand>() {

    private val service: ConfigurationService by instance()
    private val view: DatabaseConfigurationView by instance()

    override fun DirectDI.createCommand() = instance<ConfigDatabaseAddCommand>()

    private val dbConfig by lazy { global.requireEnvironment().requireDatabaseConfiguration() }

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
        coEvery {
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

        coVerifySequence {
            service.addDatabase(expectedParam)
            view.render(dbConfig)
        }
        confirmVerified(service, view)
        assertThat(testConsole).output().isEqualTo(render)
    }

    @Test
    fun `When it fails to add database, it should display error and exit`() {
        arrangeDefaultErrorHandling()
        val exception = Exception("Something went wrong")
        coEvery { service.addDatabase(any()) } returns Result.failure(exception)

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

        verifyDefaultErrorHandlingInvoked(exception)
        coVerifySequence {
            service.addDatabase(any())
        }
        confirmVerified(service, view)
    }
}