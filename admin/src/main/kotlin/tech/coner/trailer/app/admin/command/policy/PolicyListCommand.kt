package tech.coner.trailer.app.admin.command.policy

import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.io.model.PolicyCollection
import tech.coner.trailer.io.service.PolicyService
import tech.coner.trailer.presentation.library.adapter.Adapter
import tech.coner.trailer.presentation.model.PolicyCollectionModel
import tech.coner.trailer.presentation.model.PolicyModel
import tech.coner.trailer.presentation.text.view.TextCollectionView

class PolicyListCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "list",
    help = "List policies"
) {

    override val diContext = diContextDataSession()
    private val service: PolicyService by instance()
    private val adapter: tech.coner.trailer.presentation.library.adapter.Adapter<PolicyCollection, PolicyCollectionModel> by instance()
    private val view: TextCollectionView<PolicyModel, PolicyCollectionModel> by instance()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val policies = service.list()
        echo(view(adapter(policies)))
    }
}