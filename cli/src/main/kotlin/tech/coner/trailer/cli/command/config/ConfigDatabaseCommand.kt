package tech.coner.trailer.cli.command.config

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

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
