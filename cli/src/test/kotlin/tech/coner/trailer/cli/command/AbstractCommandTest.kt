package tech.coner.trailer.cli.command

import com.github.ajalt.clikt.core.context
import java.nio.file.Path
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.DI
import org.kodein.di.DIAware
import tech.coner.trailer.cli.clikt.StringBufferConsole
import tech.coner.trailer.cli.di.mockkRendererModule
import tech.coner.trailer.cli.di.mockkViewModule
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.di.mockkConstraintModule
import tech.coner.trailer.di.mockkIoModule
import tech.coner.trailer.di.mockkServiceModule
import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.TestConfigurations
import tech.coner.trailer.io.TestEnvironments

abstract class AbstractCommandTest<C : BaseCommand> : DIAware, CoroutineScope
{
    lateinit var command: C

    override val di = DI.lazy {
        fullContainerTreeOnError = true
        fullDescriptionOnError = true
        importAll(
            mockkIoModule,
            mockkConstraintModule,
            mockkServiceModule,
            testCliktModule,
            mockkViewModule,
            mockkRendererModule
        )
    }

    override val coroutineContext = Dispatchers.Default + Job()

    lateinit var global: GlobalModel
    lateinit var testConsole: StringBufferConsole

    open fun preSetup() = Unit

    @TempDir lateinit var root: Path

    abstract val setupGlobal: GlobalModel.() -> Unit
    lateinit var tempEnvironmentTestConfigurations: TestConfigurations
    lateinit var tempEnvironmentConfiguration: Configuration
    val setupGlobalWithTempEnvironment: GlobalModel.() -> Unit = {
        tempEnvironmentTestConfigurations = TestConfigurations(root)
        tempEnvironmentConfiguration = tempEnvironmentTestConfigurations.testConfiguration()
        val dbConfig = tempEnvironmentTestConfigurations.testDatabaseConfigurations.all.single { it.default }
        environment = TestEnvironments.temporary(di, root, tempEnvironmentConfiguration, dbConfig)
    }

    @BeforeEach
    fun setup() {
        preSetup()
        testConsole = StringBufferConsole()
        global = GlobalModel()
            .apply(setupGlobal)
        command = createCommand(di, global)
            .context {
                console = testConsole
            }
        postSetup()
    }

    @AfterEach
    fun after() {
        command.cancel()
        cancel()
    }

    open fun postSetup() = Unit

    protected abstract fun createCommand(di: DI, global: GlobalModel): C
}