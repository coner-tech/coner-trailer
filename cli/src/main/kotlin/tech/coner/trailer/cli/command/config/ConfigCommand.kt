package tech.coner.trailer.cli.command.config

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.command.PermitNoDatabaseChosen

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