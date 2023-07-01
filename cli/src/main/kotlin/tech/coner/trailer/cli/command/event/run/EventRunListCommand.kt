package tech.coner.trailer.cli.command.event.run

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import org.kodein.di.DI
import org.kodein.di.factory
import org.kodein.di.instance
import tech.coner.trailer.Policy
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.di.Format
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.RunService
import tech.coner.trailer.render.RunsRenderer
import java.util.*

class EventRunListCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "list",
    help = "List the runs at an event"
) {

    override val diContext = diContextDataSession()

    private val eventService: EventService by instance()
    private val runService: RunService by instance()
    private val rendererFactory: (Policy) -> RunsRenderer by factory(Format.TEXT)

    private val eventId: UUID by argument().convert { toUuid(it) }

    override suspend fun coRun() {
        val event = eventService.findByKey(eventId).getOrThrow()
        val runs = runService.list(event).getOrThrow()
        echo(rendererFactory(event.policy).render(runs))
    }
}