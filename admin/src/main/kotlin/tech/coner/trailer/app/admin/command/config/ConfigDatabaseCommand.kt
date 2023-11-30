package tech.coner.trailer.app.admin.command.config

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel

class ConfigDatabaseCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
        name = "database",
        help = "Grouping of database configuration commands"
) {

    override suspend fun CoroutineScope.coRun() = Unit
}
