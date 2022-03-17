package tech.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.view.EventTableView
import tech.coner.trailer.io.service.EventService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance

class EventListCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
    name = "list",
    help = "List Events"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: EventService by instance()
    private val view: EventTableView by instance()

    override fun run() = diContext.use {
        echo(view.render(service.list()))
    }
}