package tech.coner.trailer.app.admin.command.motorsportreg

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel

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