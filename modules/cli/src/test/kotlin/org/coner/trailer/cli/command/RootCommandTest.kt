package org.coner.trailer.cli.command

import assertk.all
import assertk.assertThat
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import assertk.assertions.isSameAs
import assertk.assertions.messageContains
import com.github.ajalt.clikt.core.BadParameterValue
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RootCommandTest {

    lateinit var command: RootCommand

    @RelaxedMockK
    lateinit var config: ConfigurationService

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        every { config.setup() }.answers { Unit }
        every { config.listDatabasesByName() }.answers { databasesByName() }
        every { config.getDefaultDatabase() }.returns(barDatabase)
        command = RootCommand(config)
    }

    @Test
    fun `When given --database with existing database name it should use it`() {
        // foo exists
        every { config.getDatabase("foo") }.returns(fooDatabase)

        command.parse(arrayOf("--database", "foo"))

        assertThat(command.currentContext.obj)
                .isNotNull()
                .isInstanceOf(RootCommand.Payload::class)
                .all {
                    databaseConfiguration().isSameAs(fooDatabase)
                }
    }

    @Test
    fun `When given --database with invalid name it should fail`() {
        // baz does not exist

        val actual = assertThrows<BadParameterValue> {
            command.parse(arrayOf("--database", "baz"))
        }

        assertThat(actual).all {
            messageContains("--database")
            messageContains("baz")
        }
    }

    @Test
    fun `When not given --database it should use the default if available`() {
        command.parse(emptyArray())

        verify { config.getDefaultDatabase() }
        assertThat(command.currentContext.obj)
                .isNotNull()
                .isInstanceOf(RootCommand.Payload::class)
                .databaseConfiguration().isSameAs(barDatabase)
    }
}

private val fooDatabase = DatabaseConfiguration(
        name = "foo",
        crispyFishDatabase = createTempDir(),
        snoozleDatabase = createTempDir(),
        default = false
)
private val barDatabase = DatabaseConfiguration(
        name = "bar",
        crispyFishDatabase = createTempDir(),
        snoozleDatabase = createTempDir(),
        default = true
)
private fun databasesByName() = mapOf(
        fooDatabase.name to fooDatabase,
        barDatabase.name to barDatabase
)