package org.coner.trailer.cli.command.config

import assertk.assertThat
import assertk.assertions.contains
import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.context
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.io.ConfigurationService
import org.coner.trailer.cli.io.TestDatabaseConfigurations
import org.coner.trailer.cli.view.DatabaseConfigurationView
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import java.nio.file.Path
import java.util.*

@ExtendWith(MockKExtension::class)
class ConfigDatabaseGetCommandTest {

    lateinit var command: ConfigDatabaseGetCommand

    @MockK
    lateinit var service: ConfigurationService
    @MockK
    lateinit var view: DatabaseConfigurationView

    @TempDir
    lateinit var temp: Path

    lateinit var testConsole: StringBufferConsole
    lateinit var dbConfigs: org.coner.trailer.cli.io.TestDatabaseConfigurations

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        dbConfigs = org.coner.trailer.cli.io.TestDatabaseConfigurations(temp)
        command = ConfigDatabaseGetCommand(
            di = DI {
                bind<ConfigurationService>() with instance(service)
                bind<DatabaseConfigurationView>() with instance(view)
            }
        ).context {
            console = testConsole
        }
    }

    @Test
    fun `When given valid name option it should get it`() {
        arrangeWithTestDatabaseConfigurations()
        val render = "view.render(foo) => ${UUID.randomUUID()}"
        every { view.render(dbConfigs.foo) }.returns(render)

        command.parse(arrayOf("foo"))

        verifyOrder {
            service.listDatabasesByName()
            view.render(dbConfigs.foo)
        }
        confirmVerified(service, view)
        assertThat(testConsole.output.contains(render))
    }

    @Test
    fun `When given invalid name option it should fail`() {
        val baz = "baz"
        check(!dbConfigs.allByName.contains(baz)) // baz is not a valid dbConfig

        arrangeWithTestDatabaseConfigurations()

        assertThrows<Abort> {
            command.parse(arrayOf(baz))
        }

        verify { service.listDatabasesByName() }
        confirmVerified(service, view)
        assertThat(testConsole.output).contains("No database found with name")
    }
}

private fun ConfigDatabaseGetCommandTest.arrangeWithTestDatabaseConfigurations() {
    every { service.listDatabasesByName() }.returns(dbConfigs.allByName)

}