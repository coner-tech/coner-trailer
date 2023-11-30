package tech.coner.trailer.app.admin.command.event.run

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import java.util.UUID
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.EventContext
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.util.clikt.toUuid
import tech.coner.trailer.io.service.EventContextService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.RunCollectionModel
import tech.coner.trailer.presentation.model.RunModel
import tech.coner.trailer.presentation.text.view.TextCollectionView

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
    private val eventContextService: EventContextService by instance()
    private val adapter: Adapter<EventContext, RunCollectionModel> by instance()
    private val view: TextCollectionView<RunModel, RunCollectionModel> by instance()

    private val eventId: UUID by argument().convert { toUuid(it) }

    override suspend fun CoroutineScope.coRun() {
        val eventContext = eventService.findByKey(eventId).getOrThrow()
            .let { eventContextService.load(it).getOrThrow() }
        echo(view(adapter(eventContext)))
    }
}