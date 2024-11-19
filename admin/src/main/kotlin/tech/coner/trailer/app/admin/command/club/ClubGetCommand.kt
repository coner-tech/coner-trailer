package tech.coner.trailer.app.admin.command.club

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.io.service.ClubService
import tech.coner.trailer.presentation.model.club.ClubDetailModel
import tech.coner.trailer.presentation.presenter.club.ClubDetailPresenter
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

    private val presenter: ClubDetailPresenter by instance()
    val textView: TextView<ClubDetailModel> by instance()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        presenter.load()
            .await()
            .getOrThrow()
            .onLeft {
                when (it) {
                    ClubService.GetFailure.NotFound -> echo("Club not found")
                }
            }
            .onRight {
                TODO("echo render of it")
            }
        Unit
    }
}