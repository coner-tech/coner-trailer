package tech.coner.trailer.app.admin.command.config

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel

class ConfigDatabaseSnoozleCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "snoozle",
    help = """
        Subcommands to manage the Snoozle database of Coner Trailer.
    """.trimIndent()
) {
    override suspend fun CoroutineScope.coRun() = Unit
}