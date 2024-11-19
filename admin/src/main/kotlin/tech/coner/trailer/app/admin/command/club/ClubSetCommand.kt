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
import tech.coner.trailer.domain.entity.Club
import tech.coner.trailer.io.service.ClubService
import tech.coner.trailer.presentation.model.ClubModel
import tech.coner.trailer.presentation.library.presenter.Presenter
import tech.coner.trailer.presentation.model.club.ClubDetailModel
import tech.coner.trailer.presentation.presenter.club.ClubDetailPresenter
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

    private val presenter: ClubDetailPresenter by instance()
    private val textView: TextView<ClubModel> by instance()

    private val name: String by option().required()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        presenter.load()
            .await()
            .getOrThrow()
            .onLeft {
                when (it) {
                    ClubService.GetFailure.NotFound -> presenter.create(
                        ClubDetailModel(name = name)
                    )
                }
            }
            .onRight {
                presenter.name.value = name
                presenter.commit()
            }
        Unit
    }
}