package tech.coner.trailer.cli.command.event.run

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import java.util.UUID
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.di.Format
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.RunService
import tech.coner.trailer.render.RunRenderer

class EventRunLatestCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "latest",
    help = "List the latest runs at an event"
) {

    override val diContext = diContextDataSession()

    private val eventService: EventService by instance()
    private val runService: RunService by instance()
    private val renderer: RunRenderer by instance { Format.TEXT }

    private val eventId: UUID by argument().convert { toUuid(it) }
    private val count: Int by option().int().default(5)

    override suspend fun coRun() {
        val event = eventService.findByKey(eventId).getOrThrow()
        val runs = runService.latest(event, count).getOrThrow()
        echo(renderer.render(runs))
    }
}