package tech.coner.trailer.cli.command.club

import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.view.ClubView
import tech.coner.trailer.io.service.ClubService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance

class ClubSetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    global = global,
    name = "set",
    help = "Set (create or update) Club properties"
), DIAware by di {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }

    private val service: ClubService by instance()
    private val view: ClubView by instance()

    private val name: String by option().required()

    override fun run() {
        val club = service.createOrUpdate(
            name = name
        )
        echo(view.render(club))
    }
}