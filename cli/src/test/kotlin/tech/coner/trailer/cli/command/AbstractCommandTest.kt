package tech.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import io.mockk.junit5.MockKExtension
import tech.coner.trailer.cli.clikt.StringBufferConsole
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext

@ExtendWith(MockKExtension::class)
abstract class AbstractCommandTest<C> : DIAware
    where C : CliktCommand, C : DIAware
{
    lateinit var command: C

    override val di: DI by lazy { createDi() }
    override val diContext = diContext { command.diContext.value }
    lateinit var global: GlobalModel
    lateinit var testConsole: StringBufferConsole

    open fun preSetup() = Unit

    @BeforeEach
    fun setup() {
        preSetup()
        testConsole = StringBufferConsole()
        global = GlobalModel()
        command = createCommand(di, global)
            .context {
                console = testConsole
            }
        postSetup()
    }

    open fun postSetup() = Unit

    protected abstract fun createDi(): DI

    protected abstract fun createCommand(di: DI, global: GlobalModel): C
}