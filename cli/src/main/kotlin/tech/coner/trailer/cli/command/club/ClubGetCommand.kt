package tech.coner.trailer.cli.command.club

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.succeedOrThrow
import tech.coner.trailer.presentation.model.ClubModel
import tech.coner.trailer.presentation.presenter.club.ClubPresenter
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

    private val presenter: ClubPresenter by instance()
    val textView: TextView<ClubModel> by instance()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        backgroundCoroutineScope.launch { presenter.load() }
        presenter.awaitLoadedItemModel()
            .succeedOrThrow {
                echo(textView(it))
            }
    }
}