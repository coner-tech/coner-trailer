package tech.coner.trailer.cli.command.policy

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

class PolicyCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    help = "Manage policies"
) {
    override suspend fun CoroutineScope.coRun() {}
}