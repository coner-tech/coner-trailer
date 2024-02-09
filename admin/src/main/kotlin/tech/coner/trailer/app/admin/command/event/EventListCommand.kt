package tech.coner.trailer.app.admin.command.event

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.Event
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.presentation.library.adapter.Adapter
import tech.coner.trailer.presentation.model.EventDetailCollectionModel
import tech.coner.trailer.presentation.model.EventDetailModel
import tech.coner.trailer.presentation.text.view.TextCollectionView

class EventListCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "list",
    help = "List Events"
) {

    override val diContext = diContextDataSession()
    private val service: EventService by instance()
    private val adapter: tech.coner.trailer.presentation.library.adapter.Adapter<Collection<Event>, EventDetailCollectionModel> by instance()
    private val view: TextCollectionView<EventDetailModel, EventDetailCollectionModel> by instance()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        echo(view(adapter(service.list())))
    }
}