package tech.coner.trailer.app.admin.command.person

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.Person
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.presentation.library.adapter.Adapter
import tech.coner.trailer.presentation.model.PersonCollectionModel
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.text.view.TextCollectionView

class PersonListCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "list",
    help = "List all people"
) {

    override val diContext = diContextDataSession()
    private val service: PersonService by instance()
    private val adapter: tech.coner.trailer.presentation.library.adapter.Adapter<Collection<Person>, PersonCollectionModel> by instance()
    private val view: TextCollectionView<PersonDetailModel, PersonCollectionModel> by instance()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        echo(view(adapter(service.list())))
    }
}