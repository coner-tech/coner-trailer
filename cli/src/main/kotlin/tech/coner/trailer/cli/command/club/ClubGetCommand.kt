package tech.coner.trailer.cli.command.club

import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.ClubView
import tech.coner.trailer.io.service.ClubService

class ClubGetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    global = global,
    name = "get",
    help = "Get the Club properties"
), DIAware by di {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }

    private val service: ClubService by instance()
    private val view: ClubView by instance()

    override fun run() {
        echo(view.render(service.get()))
    }
}