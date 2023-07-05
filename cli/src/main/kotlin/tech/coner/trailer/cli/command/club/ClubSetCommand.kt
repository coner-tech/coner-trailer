package tech.coner.trailer.cli.command.club

import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.io.service.ClubService
import tech.coner.trailer.render.view.ClubViewRenderer

class ClubSetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "set",
    help = "Set (create or update) Club properties"
) {

    override val diContext = diContextDataSession()

    private val service: ClubService by instance()
    private val view: ClubViewRenderer by instance(Format.TEXT)

    private val name: String by option().required()

    override suspend fun coRun() {
        val club = service.createOrUpdate(
            name = name
        )
        echo(view(club))
    }
}