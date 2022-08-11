package tech.coner.trailer.cli.command.rankingsort

import org.kodein.di.DI
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel

class RankingSortCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global
) {

    override suspend fun coRun() = Unit
}