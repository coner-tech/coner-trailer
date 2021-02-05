package org.coner.trailer.cli.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualToIgnoringGivenProperties
import assertk.assertions.isTrue
import assertk.assertions.prop
import com.github.ajalt.clikt.core.BadParameterValue
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.io.TestDatabaseConfigurations
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import java.nio.file.Path

class ConfigDatabaseSetDefaultCommandTest {

    lateinit var command: ConfigDatabaseSetDefaultCommand

    @MockK
    lateinit var service: ConfigurationService

    @TempDir
    lateinit var temp: Path

    lateinit var dbConfigs: TestDatabaseConfigurations

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        dbConfigs = TestDatabaseConfigurations(temp)
    }

    @Test
    fun `When given valid name it should set default`() {
        arrangeWithTestDatabaseConfigurations()
        val slot = slot<DatabaseConfiguration>()
        every { service.configureDatabase(capture(slot)) } answers { Unit }

        command.parse(arrayOf("--name", "foo"))

        verifyOrder {
            service.listDatabasesByName()
            service.noDatabase
            service.configureDatabase(any())
        }
        confirmVerified(service)
        assertThat(slot.captured).all {
            isEqualToIgnoringGivenProperties(dbConfigs.foo, DatabaseConfiguration::default)
            prop("default") { it.default }.isTrue()
        }
    }

    @Test
    fun `When given invalid name it should fail`() {
        val baz = "baz"
        check(!dbConfigs.allByName.contains(baz)) // baz is not a valid dbConfig

        every { service.listDatabasesByName() } returns mapOf(
                dbConfigs.noDatabase.name to dbConfigs.noDatabase
        )

        assertThrows<BadParameterValue> {
            command.parse(arrayOf("--name", baz))
        }

        verifyOrder {
            service.listDatabasesByName()
        }
        confirmVerified(service)
    }
}

private fun ConfigDatabaseSetDefaultCommandTest.arrangeWithTestDatabaseConfigurations() {
    every { service.listDatabasesByName() } returns(dbConfigs.allByName)
    every { service.noDatabase } returns(dbConfigs.noDatabase)
    command = ConfigDatabaseSetDefaultCommand(
        di = DI {
            bind<ConfigurationService>() with instance(service)
        }
    )
}