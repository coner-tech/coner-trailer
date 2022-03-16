package org.coner.trailer.cli.command.club

import org.coner.trailer.cli.command.BaseCommand
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.view.ClubView
import org.coner.trailer.io.service.ClubService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance

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