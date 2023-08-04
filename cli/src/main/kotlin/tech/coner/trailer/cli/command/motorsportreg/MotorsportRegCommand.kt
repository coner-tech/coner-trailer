package tech.coner.trailer.cli.command.motorsportreg

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

class MotorsportRegCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "motorsportreg",
    help = "Interact with MotorsportReg"
) {

    override suspend fun CoroutineScope.coRun() = Unit
}