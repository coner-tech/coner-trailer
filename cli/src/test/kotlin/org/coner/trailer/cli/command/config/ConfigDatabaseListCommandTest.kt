package org.coner.trailer.cli.command.config

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.io.ConfigurationService
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
class ConfigDatabaseListCommandTest {

    lateinit var command: ConfigDatabaseListCommand

    @MockK
    lateinit var service: ConfigurationService
    @MockK
    lateinit var view: DatabaseConfigurationView

    @TempDir
    lateinit var temp: Path

    lateinit var dbConfigs: TestDatabaseConfigurations
    lateinit var testConsole: StringBufferConsole

    @BeforeEach
    fun before() {
        dbConfigs = TestDatabaseConfigurations(temp)
        testConsole = StringBufferConsole()
        command = ConfigDatabaseListCommand(
            di = DI {
                bind<ConfigurationService>() with instance(service)
                bind<DatabaseConfigurationView>() with instance(view)
            },
        ).context {
            console = testConsole
        }
    }

    @Test
    fun `It should list databases`() {
        every { service.listDatabases() } returns dbConfigs.all
        val output = """
            foo => ${UUID.randomUUID()}
            bar => ${UUID.randomUUID()}
        """.trimIndent()
        every { view.render(dbConfigs.all) } returns(output)

        command.parse(emptyArray())

        verifySequence {
            service.listDatabases()
            view.render(dbConfigs.all)
        }
        assertThat(testConsole.output, "console output").isEqualTo(output)
    }
}