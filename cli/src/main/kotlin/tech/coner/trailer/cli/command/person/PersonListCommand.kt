package tech.coner.trailer.cli.command.person

import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.render.view.PersonCollectionViewRenderer

class PersonListCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "list",
    help = "List all people"
) {

    override val diContext = diContextDataSession()
    private val service: PersonService by instance()
    private val view: PersonCollectionViewRenderer by instance(Format.TEXT)

    override suspend fun coRun() = diContext.use {
        echo(view.render(service.list()))
    }
}