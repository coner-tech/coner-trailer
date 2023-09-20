package tech.coner.trailer.cli.command.event.run

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.io.service.EventContextService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.presentation.adapter.EventContextRunCollectionModelAdapter
import tech.coner.trailer.presentation.model.RunCollectionModel
import tech.coner.trailer.presentation.model.RunModel
import tech.coner.trailer.presentation.text.view.TextCollectionView
import tech.coner.trailer.presentation.text.view.TextRunsView
import tech.coner.trailer.presentation.text.view.TextView
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
    private val eventContextService: EventContextService by instance()
    private val adapter: EventContextRunCollectionModelAdapter by instance()
    private val view: TextCollectionView<RunModel, RunCollectionModel> by instance()

    private val eventId: UUID by argument().convert { toUuid(it) }

    override suspend fun CoroutineScope.coRun() {
        val eventContext = eventService.findByKey(eventId).getOrThrow()
            .let { eventContextService.load(it).getOrThrow() }
        echo(this@EventRunListCommand.view(this@EventRunListCommand.adapter(eventContext)))
    }
}