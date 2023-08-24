package tech.coner.trailer.cli.command.event.run

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.int
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.factory
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.util.succeedOrThrow
import tech.coner.trailer.presentation.Strings
import tech.coner.trailer.presentation.model.RunCollectionModel
import tech.coner.trailer.presentation.model.RunModel
import tech.coner.trailer.presentation.presenter.run.EventRunLatestPresenter
import tech.coner.trailer.presentation.presenter.run.EventRunLatestPresenterFactory
import tech.coner.trailer.presentation.text.view.TextCollectionView
import java.util.*

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

    private val presenterFactory: EventRunLatestPresenterFactory by factory()
    private val textView: TextCollectionView<RunModel, RunCollectionModel> by instance()

    private val count: Int? by option().int()
    private val eventId: UUID by argument().convert { toUuid(it) }

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val presenter = presenterFactory(EventRunLatestPresenter.Argument(eventId))
        backgroundCoroutineScope.launch { presenter.load() }
        presenter.awaitLoadedItemModel()
            .succeedOrThrow { model ->
                count?.also { model.count = it }
            }
        presenter.commit()
            .succeedOrThrow { model ->
                echo(textView(model.latestRuns))
            }
    }
}