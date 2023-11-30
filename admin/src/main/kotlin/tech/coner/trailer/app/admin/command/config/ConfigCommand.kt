package tech.coner.trailer.app.admin.command.config

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.command.PermitNoDatabaseChosen

class ConfigCommand(
    di: DI,
    global: GlobalModel,
): BaseCommand(
    di = di,
    global = global,
    name = "config",
    help = "Grouping of configuration commands"
), PermitNoDatabaseChosen {

    override suspend fun CoroutineScope.coRun() = Unit
}