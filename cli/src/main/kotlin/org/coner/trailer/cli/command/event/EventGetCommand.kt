package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.di.use
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.EventView
import org.coner.trailer.io.service.EventService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import java.util.*

class EventGetCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
    name = "get",
    help = "Get an Event"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: EventService by instance()
    private val view: EventView by instance()

    private val id: UUID by argument().convert { toUuid(it) }

    override fun run() = diContext.use {
        val get = service.findById(id)
        echo(view.render(get))
    }
}