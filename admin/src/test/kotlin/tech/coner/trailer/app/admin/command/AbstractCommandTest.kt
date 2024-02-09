package tech.coner.trailer.app.admin.command

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotZero
import com.github.ajalt.clikt.testing.CliktCommandTestResult
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.newSingleThreadContext
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import org.kodein.di.*
import tech.coner.trailer.app.admin.clikt.statusCode
import tech.coner.trailer.app.admin.clikt.stderr
import tech.coner.trailer.app.admin.di.Invocation
import tech.coner.trailer.app.admin.presentation.model.BaseCommandErrorModel
import tech.coner.trailer.io.Configuration
import tech.coner.trailer.io.TestConfigurations
import tech.coner.trailer.io.TestEnvironments
import tech.coner.trailer.presentation.library.adapter.Adapter
import tech.coner.trailer.presentation.text.view.TextView
import java.nio.file.Path
import kotlin.coroutines.CoroutineContext

abstract class AbstractCommandTest<C : BaseCommand> : DIAware, CoroutineScope
{
    lateinit var invocation: Invocation
    lateinit var global: GlobalModel
    lateinit var command: C
    lateinit var errorAdapter: tech.coner.trailer.presentation.library.adapter.Adapter<Throwable, BaseCommandErrorModel>
    lateinit var errorView: TextView<BaseCommandErrorModel>

    private val mainThreadSurrogate = newSingleThreadContext("CLI Main Thread Test Surrogate")
    override val coroutineContext: CoroutineContext = mainThreadSurrogate

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
        val direct = di.direct
        invocation = direct.provider<Invocation>().invoke()
        val invocationDirect = direct.on(invocation)
        global = invocationDirect.instance()
        errorAdapter = invocationDirect.instance()
        errorView = invocationDirect.instance()
        global.apply(setupGlobal)
        command = invocationDirect.createCommand()
        postSetup()
    }

    @AfterEach
    fun afterAbstract() {
        mainThreadSurrogate.close()
        command.cancel()
    }

    open fun postSetup() = Unit

    protected abstract fun DirectDI.createCommand(): C

    val defaultErrorHandlingViewRenders = "defaultErrorHandlingViewRenders"
    private val errorModel: BaseCommandErrorModel by lazy { mockk() }

    protected fun arrangeDefaultErrorHandling() {
        every { errorAdapter(any()) } returns errorModel
        every { errorView(any()) } returns defaultErrorHandlingViewRenders
    }

    protected fun verifyDefaultErrorHandlingInvoked(result: CliktCommandTestResult, t: Throwable) {
        verifySequence {
            errorAdapter(t)
            errorView(errorModel)
        }
        assertThat(result).all {
            statusCode().isNotZero()
            stderr().isEqualTo(defaultErrorHandlingViewRenders)
        }
    }
}
