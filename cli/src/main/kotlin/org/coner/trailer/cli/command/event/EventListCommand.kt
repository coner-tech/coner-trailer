package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.di.use
import org.coner.trailer.cli.view.EventTableView
import org.coner.trailer.io.service.EventService
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