package tech.coner.trailer.cli.command.rankingsort

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.view.RankingSortView
import tech.coner.trailer.io.service.RankingSortService

class RankingSortListCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "list",
    help = "List ranking sorts"
) {

    override val diContext = diContextDataSession()
    private val service: RankingSortService by instance()
    private val view: RankingSortView by instance()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        echo(view.render(service.list()))
    }
}