package tech.coner.trailer.cli.command.policy

import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.Policy
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.io.service.PolicyService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.adapter.PolicyModelAdapter
import tech.coner.trailer.presentation.model.PolicyModel
import tech.coner.trailer.presentation.text.view.TextView
import java.util.*

class PolicyGetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "get",
    help = "Get a policy"
) {

    override val diContext = diContextDataSession()
    private val service: PolicyService by instance()
    private val adapter: Adapter<Policy, PolicyModel> by instance()
    private val view: TextView<PolicyModel> by instance()

    private val find: Find by mutuallyExclusiveOptions(
        option("--id").convert { Find.ById(id = toUuid(it)) },
        option("--name").convert { Find.ByName(name = it) }
    ).required()
    sealed class Find {
        class ById(val id: UUID) : Find()
        class ByName(val name: String) : Find()
    }

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val policy = when (val find = find) {
            is Find.ById -> service.findById(find.id)
            is Find.ByName -> service.findByName(find.name)
        }
        echo(view(adapter(policy)))
    }
}