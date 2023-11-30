package tech.coner.trailer.app.admin.command.rankingsort

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel

class RankingSortCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global
) {

    override suspend fun CoroutineScope.coRun() = Unit
}