package tech.coner.trailer.cli.command.config

import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import java.util.logging.Logger.global

class ConfigDatabaseCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
        name = "database",
        help = "Grouping of database configuration commands"
) {

    override suspend fun coRun() = Unit
}
