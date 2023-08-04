package tech.coner.trailer.cli.command.season

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.view.SeasonTableView
import tech.coner.trailer.io.service.SeasonService

class SeasonListCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "list",
    help = "List Seasons"
) {

    override val diContext = diContextDataSession()
    private val service: SeasonService by instance()
    private val view: SeasonTableView by instance()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        echo(view.render(service.list()))
    }
}