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
import tech.coner.trailer.cli.di.mockkViewModule
import tech.coner.trailer.cli.di.testCliktModule
import tech.coner.trailer.di.mockkDatabaseModule
import tech.coner.trailer.di.mockkIoModule
import tech.coner.trailer.io.TestConfigurations
import tech.coner.trailer.io.TestEnvironments

abstract class AbstractCommandTest<C : BaseCommand> : DIAware, CoroutineScope
{
    lateinit var command: C

    override val di = DI.lazy {
        importAll(
            mockkIoModule,
            mockkDatabaseModule(),
            testCliktModule,
            mockkViewModule
        )
    }

    override val coroutineContext = Dispatchers.Default + Job()

    lateinit var global: GlobalModel
    lateinit var testConsole: StringBufferConsole

    open fun preSetup() = Unit

    @TempDir lateinit var root: Path

    abstract val setupGlobal: GlobalModel.() -> Unit
    val setupGlobalWithTempEnvironment: GlobalModel.() -> Unit = {
        val testConfigs = TestConfigurations(root)
        val config = testConfigs.testConfiguration()
        val dbConfig = testConfigs.testDatabaseConfigurations.all.single { it.default }
        environment = TestEnvironments.temporary(di, root, config, dbConfig)
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