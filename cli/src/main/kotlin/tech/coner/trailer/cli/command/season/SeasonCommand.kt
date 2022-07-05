package tech.coner.trailer.cli.command.season

import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

class SeasonCommand(
    di: DI,
    global : GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    help = "Manage seasons"
) {

    override suspend fun coRun() = Unit
}