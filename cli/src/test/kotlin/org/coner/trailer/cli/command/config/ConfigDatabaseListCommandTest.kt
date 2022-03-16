package org.coner.trailer.cli.command.config

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.coner.trailer.cli.clikt.StringBufferConsole
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.view.DatabaseConfigurationView
import org.coner.trailer.di.ConfigurationServiceArgument
import org.coner.trailer.io.ConfigurationService
import org.coner.trailer.io.TestDatabaseConfigurations
import org.coner.trailer.io.TestEnvironments
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
class ConfigDatabaseListCommandTest : DIAware {

    lateinit var command: ConfigDatabaseListCommand

    override val di = DI.lazy {
        bindFactory { _: ConfigurationServiceArgument -> service }
        bindInstance { view }
    }

    @MockK lateinit var service: ConfigurationService
    @MockK lateinit var view: DatabaseConfigurationView

    @TempDir lateinit var root: Path

    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel
    lateinit var dbConfigs: TestDatabaseConfigurations

    @BeforeEach
    fun before() {
        testConsole = StringBufferConsole()
        dbConfigs = TestDatabaseConfigurations(root)
        global = GlobalModel()
            .apply { environment = TestEnvironments.temporary(di, root, dbConfigs.bar) }
        command = ConfigDatabaseListCommand(di, global)
            .context { console = testConsole }
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