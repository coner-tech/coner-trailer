package tech.coner.trailer.cli.command.person

import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

class PersonCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    help = "Manage people (records)"
) {

    override suspend fun coRun() = Unit
}