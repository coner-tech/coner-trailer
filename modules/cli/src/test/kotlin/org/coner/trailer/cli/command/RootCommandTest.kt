package org.coner.trailer.cli.command

import assertk.all
import assertk.assertThat
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import assertk.assertions.isSameAs
import assertk.assertions.messageContains
import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.BadParameterValue
import com.github.ajalt.clikt.core.subcommands
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.coner.trailer.cli.io.ConfigurationService
import org.coner.trailer.cli.io.DatabaseConfiguration
import org.coner.trailer.cli.service.StubService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.kodein.di.*

class RootCommandTest {

    lateinit var command: RootCommand

    @RelaxedMockK
    lateinit var config: ConfigurationService
    @RelaxedMockK
    lateinit var noDatabase: DatabaseConfiguration

    lateinit var di: DI

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When given --database with existing database name it should use it`() {
        arrangeWithDatabases()
        // foo exists
        every { config.getDatabase("foo") }.returns(fooDatabase)

        command.parse(arrayOf("--database", "foo"))

        assertThat(command.currentContext.obj)
                .isNotNull()
                .isInstanceOf(DI::class)
                .transform { it.direct.instance<DatabaseConfiguration>() }
                .isSameAs(fooDatabase)
    }

    @Test
    fun `When given --database with invalid name it should fail`() {
        arrangeWithDatabases()
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
        arrangeWithDatabases()
        val defaultDatabase = databasesByName()
                .values
                .single { it.default }

        command.parse(emptyArray())

        verify { config.getDefaultDatabase() }
        assertThat(command.currentContext.obj)
                .isNotNull()
                .isInstanceOf(DI::class)
                .transform { it.direct.instance<DatabaseConfiguration>() }
                .isSameAs(defaultDatabase)
    }

    @Test
    fun `When no database chosen and subcommand requires database choice, it should abort`() {
        arrangeWithoutDatabasesCase()

        assertThrows<Abort> {
            command.parse(arrayOf("stub"))
        }
    }

    @Test
    fun `When no database chosen and subcommand permits no database, it should continue without database`() {
        arrangeWithoutDatabasesCase()

        command.parse(arrayOf("config"))

        assertThat(command.currentContext.obj)
                .isNotNull()
                .isInstanceOf(DI::class)
                .given { di: DI ->
                    assertThrows<DI.NotFoundException> { di.direct.instance<DatabaseConfiguration>() }
                }
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

private fun RootCommandTest.arrangeWithDatabases() {
    di = DI {
        bind<ConfigurationService>() with instance(config)
        bind<DatabaseConfiguration>(RootCommand.NoDatabase) with instance(noDatabase)
        bind<StubService>() with singleton { StubService(
                databaseConfiguration = instance()
        ) }
    }
    every { config.setup() }.answers { Unit }
    every { config.listDatabasesByName() }.answers { databasesByName() }
    every { config.getDefaultDatabase() }.returns(barDatabase)
    command = RootCommand(di)
}

private fun RootCommandTest.arrangeWithoutDatabasesCase() {
    di = DI {
        bind<ConfigurationService>() with instance(config)
        bind<DatabaseConfiguration>(RootCommand.NoDatabase) with instance(noDatabase)
        bind<StubService>() with singleton { StubService(
                databaseConfiguration = instance()
        ) }
    }
    every { config.setup() }.answers { Unit }
    every { config.listDatabasesByName() }.answers { emptyMap() }
    every { config.getDefaultDatabase() }.returns(null)
    command = RootCommand(di)
            .subcommands(
                    ConfigCommand(),
                    StubCommand(di)
            )
}