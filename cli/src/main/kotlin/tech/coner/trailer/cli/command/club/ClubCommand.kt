package tech.coner.trailer.cli.command.club

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

class ClubCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    help = "Manage club"
) {

    override suspend fun CoroutineScope.coRun() = Unit
}