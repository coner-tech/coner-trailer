package tech.coner.trailer.app.admin.command.motorsportreg

import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.Person
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.io.service.MotorsportRegImportService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.adapter.PersonCollectionModelAdapter
import tech.coner.trailer.presentation.model.PersonCollectionModel
import tech.coner.trailer.presentation.model.PersonDetailModel
import tech.coner.trailer.presentation.text.view.TextCollectionView

class MotorsportRegMemberImportCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
        name = "import",
        help = "Import people from MotorsportReg Member records"
) {

    override val diContext = diContextDataSession()
    private val service: MotorsportRegImportService by instance()
    private val adapter: Adapter<Collection<Person>, PersonCollectionModel> by instance()
    private val view: TextCollectionView<PersonDetailModel, PersonCollectionModel> by instance()

    private val dryRun: Boolean by option(
            help = "Simulate and display what would be changed, but don't persist any changes"
    ).flag()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val result = service.importMembersAsPeople(dryRun)
        echo("Created: (${result.created.size})")
        echo(view(adapter(result.created)))
        echo()
        echo("Updated: (${result.updated.size})")
        echo(view(adapter(result.updated)))
    }
}