package tech.coner.trailer.cli.command.event

import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.render.view.EventCollectionViewRenderer

class EventListCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "list",
    help = "List Events"
) {

    override val diContext = diContextDataSession()
    private val service: EventService by instance()
    private val view: EventCollectionViewRenderer by instance(Format.TEXT)

    override suspend fun coRun() = diContext.use {
        echo(view(service.list()))
    }
}