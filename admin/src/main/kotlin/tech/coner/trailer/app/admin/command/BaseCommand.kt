package tech.coner.trailer.app.admin.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.mordant.terminal.Terminal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.kodein.di.*
import tech.coner.trailer.app.admin.presentation.model.BaseCommandErrorModel
import tech.coner.trailer.app.admin.util.CliBackgroundCoroutineScope
import tech.coner.trailer.app.admin.util.CliMainCoroutineScope
import tech.coner.trailer.di.DataSessionHolder
import tech.coner.trailer.di.EnvironmentHolder
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.text.view.TextView
import kotlin.coroutines.CoroutineContext

abstract class BaseCommand(
    di: DI,
    protected val global: GlobalModel,
    help: String = "",
    epilog: String = "",
    name: String? = null,
    invokeWithoutSubcommand: Boolean = false,
    printHelpOnEmptyArgs: Boolean = false,
    helpTags: Map<String, String> = emptyMap(),
    autoCompleteEnvvar: String? = "",
) : CliktCommand(
    help = help,
    epilog = epilog,
    name = name,
    invokeWithoutSubcommand = invokeWithoutSubcommand,
    printHelpOnEmptyArgs = printHelpOnEmptyArgs,
    helpTags = helpTags,
    autoCompleteEnvvar = autoCompleteEnvvar
),
    DIAware by di,
    CoroutineContext by di.direct.instance<CliMainCoroutineScope>().coroutineContext
{

    val backgroundCoroutineScope: CliBackgroundCoroutineScope by lazy { direct.instance<CliBackgroundCoroutineScope>() }

    private inner class InvocationContainer(di: DIAware) : DIAware by di {
        override val diContext = diContext { global.invocation }
        val errorAdapter: Adapter<Throwable, BaseCommandErrorModel> by instance()
        val errorView: TextView<BaseCommandErrorModel> by instance()
    }
    private val invocationDi = InvocationContainer(di)

    override fun run() = runBlocking {
        try {
            coRun()
        } catch (t: Throwable) {
            with (invocationDi) {
                echo(
                    message = errorView(errorAdapter(t)),
                    err = true
                )
                val statusCode = when (t) {
                    is ProgramResult -> t.statusCode
                    else -> 1
                }
                throw ProgramResult(statusCode)
            }
        }
    }

    abstract suspend fun CoroutineScope.coRun()

    fun <C> C.diContextEnvironment(): DIContext<EnvironmentHolder>
            where C : BaseCommand {
        return diContext { global.requireEnvironment() }
    }

    fun <C> C.diContextDataSession(): DIContext<DataSessionHolder>
            where C : BaseCommand {
        return diContext { global.requireEnvironment().openDataSession() }
    }
}

