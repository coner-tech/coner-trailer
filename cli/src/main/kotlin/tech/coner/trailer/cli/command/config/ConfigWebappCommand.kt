package tech.coner.trailer.cli.command.config

import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.command.PermitNoDatabaseChosen

class ConfigWebappCommand(
    di: DI, global: GlobalModel
) : BaseCommand(
    di = di, global = global, name = "webapp", help = "Configure a webapp"
), PermitNoDatabaseChosen {

    override suspend fun coRun() = Unit
}