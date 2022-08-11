package tech.coner.trailer.cli.command.motorsportreg

import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import java.util.logging.Logger.global

class MotorsportRegCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "motorsportreg",
    help = "Interact with MotorsportReg"
) {

    override suspend fun coRun() = Unit
}