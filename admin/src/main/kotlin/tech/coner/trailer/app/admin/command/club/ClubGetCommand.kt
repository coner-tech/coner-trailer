package tech.coner.trailer.app.admin.command.club

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.factory
import org.kodein.di.instance
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.app.admin.util.succeedOrThrow
import tech.coner.trailer.presentation.model.ClubModel
import tech.coner.trailer.presentation.library.presenter.Presenter
import tech.coner.trailer.presentation.presenter.club.ClubPresenterFactory
import tech.coner.trailer.presentation.text.view.TextView

class ClubGetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "get",
    help = "Get the Club properties"
) {

    override val diContext = diContextDataSession()

    private val presenterFactory: ClubPresenterFactory by factory()
    val textView: TextView<ClubModel> by instance()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val presenter = presenterFactory(Presenter.Argument.Nothing)
        backgroundCoroutineScope.launch { presenter.load() }
        presenter.awaitLoadedItemModel()
            .succeedOrThrow {
                echo(textView(it))
            }
    }
}