package tech.coner.trailer.app.admin.command.club

import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import kotlinx.coroutines.CoroutineScope
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

    private val presenterFactory: ClubPresenterFactory by factory()
    private val textView: TextView<ClubModel> by instance()

    private val name: String by option().required()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val presenter = presenterFactory(Presenter.Argument.Nothing)
        presenter.itemModel.name = name
        presenter.commit()
            .succeedOrThrow {
                presenter.createOrUpdate().getOrThrow()
                echo(textView(it))
            }
    }
}