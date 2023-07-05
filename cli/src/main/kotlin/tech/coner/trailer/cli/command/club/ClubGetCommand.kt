package tech.coner.trailer.cli.command.club

import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.io.service.ClubService
import tech.coner.trailer.render.view.ClubViewRenderer

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
    private val view: ClubViewRenderer by instance(Format.TEXT)

    override suspend fun coRun() {
        echo(view(service.get()))
    }
}