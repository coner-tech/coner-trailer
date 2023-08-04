package tech.coner.trailer.cli.command.club

import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
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

class ClubSetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "set",
    help = "Set (create or update) Club properties"
) {

    override val diContext = diContextDataSession()

    private val presenter: ClubPresenter by instance()
    private val textView: TextView<ClubModel> by instance()

    private val name: String by option().required()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        backgroundCoroutineScope.launch { presenter.load() }
        presenter.awaitLoadedItemModel()
            .succeedOrThrow {
                it.name = name
            }
        presenter.commit()
            .succeedOrThrow {
                presenter.createOrUpdate()
                echo(textView(it))
            }
    }
}