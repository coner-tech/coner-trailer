package tech.coner.trailer.cli.command

import com.github.ajalt.clikt.core.CliktCommand
import org.kodein.di.DIAware
import org.kodein.di.DIContext
import org.kodein.di.diContext
import tech.coner.trailer.di.DataSessionHolder

abstract class BaseCommand(
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
) {

    fun <C> C.diContextDataSession(): DIContext<DataSessionHolder>
            where C : BaseCommand, C : DIAware {
        return diContext { global.requireEnvironment().openDataSession() }
    }
}

