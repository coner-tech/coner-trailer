package tech.coner.trailer.app.admin.command.policy

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.app.admin.util.clikt.toUuid
import tech.coner.trailer.io.service.PolicyService
import java.util.*

class PolicyDeleteCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "delete",
    help = "Delete a Policy"
) {

    override val diContext = diContextDataSession()
    private val service: PolicyService by instance()

    private val id: UUID by argument()
        .convert { toUuid(it) }

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val delete = service.findById(id)
        service.delete(delete)
    }
}