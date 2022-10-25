package tech.coner.trailer.cli.command.config

import assertk.all
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.core.context
import io.ktor.server.routing.RoutingPath.Companion.root
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import java.nio.file.Path
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bindProvider
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.clikt.error
import tech.coner.trailer.cli.clikt.output
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.cli.view.WebappConfigurationView
import tech.coner.trailer.di.mockkIoModule
import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.TestConfigurations
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.io.Webapp
import tech.coner.trailer.io.service.ConfigurationService

@ExtendWith(MockKExtension::class)
class ConfigWebappGetCommandTest : DIAware, CoroutineScope {

    lateinit var command: ConfigWebappGetCommand

    override val coroutineContext = Dispatchers.Main + Job()

    override val di = DI.lazy {
        import(testCliktModule)
        import(mockkIoModule)
        bindProvider { view }
    }
    override val diContext = diContext { global.requireEnvironment() }

    @TempDir
    lateinit var root: Path
    lateinit var testConsole: StringBufferConsole
    lateinit var global: GlobalModel
    val service: ConfigurationService by instance()
    @MockK lateinit var view: WebappConfigurationView

    @BeforeEach
    fun setUp() {
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply {
                val testConfigurations = TestConfigurations(root)
                val testConfig = testConfigurations.testConfiguration()
                val dbConfig = testConfigurations.testDatabaseConfigurations.foo
                environment = TestEnvironments.temporary(di, root, testConfig, dbConfig)
            }
        command = ConfigWebappGetCommand(di, global)
            .context { console = testConsole }
    }

    @AfterEach
    fun tearDown() {
        cancel()
    }

    @Test
    fun `It should get results webapp config`() {
        val webappConfig = Configuration.DEFAULT.requireWebapps().requireResults()
        coEvery { service.getWebappConfiguration(Webapp.RESULTS) } returns Result.success(webappConfig)
        val viewRender = "view rendered"
        every { view.render(any()) } returns viewRender

        command.parse(arrayOf("--webapp", "results"))

        assertThat(testConsole).all {
            output().isEqualTo(viewRender)
            error().isEmpty()
        }
        coVerifySequence {
            service.getWebappConfiguration(Webapp.RESULTS)
            view.render(Webapp.RESULTS to webappConfig)
        }
    }
}