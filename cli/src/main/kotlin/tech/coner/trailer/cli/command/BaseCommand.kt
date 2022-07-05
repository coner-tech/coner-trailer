package tech.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import kotlinx.coroutines.Job
import kotlinx.coroutines.plus
import kotlinx.coroutines.runBlocking
import org.kodein.di.*
import tech.coner.trailer.cli.di.CliCoroutineScope
import tech.coner.trailer.di.DataSessionHolder
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
    CoroutineContext by di.direct.instance<CliCoroutineScope>().coroutineContext + Job()
{

    override fun run() = runBlocking {
        coRun()
    }

    abstract suspend fun coRun()

    fun <C> C.diContextDataSession(): DIContext<DataSessionHolder>
            where C : BaseCommand {
        return diContext { global.requireEnvironment().openDataSession() }
    }
}

