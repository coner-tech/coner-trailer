package tech.coner.trailer.cli.command.club

import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.ClubView
import tech.coner.trailer.io.service.ClubService

class ClubGetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "get",
    help = "Get the Club properties"
) {

    override val diContext = diContextDataSession()

    private val service: ClubService by instance()
    private val view: ClubView by instance()

    override suspend fun coRun() {
        echo(view.render(service.get()))
    }
}