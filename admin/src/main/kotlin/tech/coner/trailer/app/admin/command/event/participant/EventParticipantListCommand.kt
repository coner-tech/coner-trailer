package tech.coner.trailer.app.admin.command.event.participant

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import java.util.UUID
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.util.clikt.toUuid
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.ParticipantService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.adapter.ParticipantCollectionModelAdapter
import tech.coner.trailer.presentation.model.ParticipantCollectionModel
import tech.coner.trailer.presentation.model.ParticipantModel
import tech.coner.trailer.presentation.text.view.TextCollectionView

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
    private val adapter: Adapter<ParticipantCollectionModelAdapter.Input, ParticipantCollectionModel> by instance()
    private val view: TextCollectionView<ParticipantModel, ParticipantCollectionModel> by instance()

    private val eventId: UUID by argument().convert { toUuid(it) }

    override suspend fun CoroutineScope.coRun() {
        val event = eventService.findByKey(eventId).getOrThrow()
        val participants = participantService.list(event).getOrThrow()
        echo(view(adapter(ParticipantCollectionModelAdapter.Input(participants, event))))
    }
}