package tech.coner.trailer.cli.command.webapp

import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

class WebappCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "webapp",
    help = "Webapp subcommands"
) {
    override suspend fun coRun() = Unit
}