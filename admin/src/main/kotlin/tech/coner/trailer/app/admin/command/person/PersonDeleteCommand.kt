package tech.coner.trailer.app.admin.command.person

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.factory
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.app.admin.util.clikt.toUuid
import tech.coner.trailer.app.admin.util.succeedOrThrow
import tech.coner.trailer.presentation.presenter.person.PersonDetailPresenter
import tech.coner.trailer.presentation.presenter.person.PersonDetailPresenterFactory
import java.util.*

class PersonDeleteCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "delete",
    help = "Delete a person"
) {

    override val diContext = diContextDataSession()
    private val presenterFactory: PersonDetailPresenterFactory by factory()

    private val id: UUID by argument()
        .convert { toUuid(it) }

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val presenter = presenterFactory(PersonDetailPresenter.Argument.GetById(id))
        backgroundCoroutineScope.launch { presenter.load() }
        presenter.awaitLoadedItemModel()
            .succeedOrThrow {
                presenter.delete()
            }
    }
}