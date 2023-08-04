package tech.coner.trailer.cli.command.event.participant

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.ParticipantService
import tech.coner.trailer.presentation.adapter.ParticipantCollectionModelAdapter
import tech.coner.trailer.presentation.text.view.TextParticipantsView
import java.util.*

class EventParticipantListCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "list",
    help = "List the participants at an event"
) {

    override val diContext = diContextDataSession()
    private val eventService: EventService by instance()
    private val participantService: ParticipantService by instance()
    private val adapter: ParticipantCollectionModelAdapter by instance()
    private val view: TextParticipantsView by instance()

    private val eventId: UUID by argument().convert { toUuid(it) }

    override suspend fun CoroutineScope.coRun() {
        val event = eventService.findByKey(eventId).getOrThrow()
        val participants = participantService.list(event).getOrThrow()
        echo(this@EventParticipantListCommand.view(this@EventParticipantListCommand.adapter(participants, event)))
    }
}