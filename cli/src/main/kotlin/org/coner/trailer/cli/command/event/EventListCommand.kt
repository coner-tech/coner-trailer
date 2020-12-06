package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import org.coner.trailer.cli.view.EventTableView
import org.coner.trailer.io.service.EventService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class EventListCommand(
    di: DI
) : CliktCommand(
    name = "list",
    help = "List Events"
), DIAware {

    override val di: DI by findOrSetObject { di }

    private val service: EventService by instance()
    private val view: EventTableView by instance()

    override fun run() {
        echo(view.render(service.list()))
    }
}