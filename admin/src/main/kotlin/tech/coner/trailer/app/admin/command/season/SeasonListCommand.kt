package tech.coner.trailer.app.admin.command.season

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.app.admin.view.SeasonTableView
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