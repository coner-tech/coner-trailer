package tech.coner.trailer.cli.command.event.participants

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.di.Format
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.ParticipantService
import tech.coner.trailer.render.ParticipantRenderer
import java.util.*

class EventParticipantsListCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
    name = "list",
    help = "List Event Participants"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val eventService: EventService by instance()
    private val participantService: ParticipantService by instance()
    private val renderer: ParticipantRenderer by instance { Format.TEXT }

    private val eventId: UUID by argument().convert { toUuid(it) }

    override fun run() {
        val event = eventService.findById(eventId)
        val participants = participantService.list(event).getOrThrow()
        echo(renderer.render(participants))
    }
}