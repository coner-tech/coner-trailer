package tech.coner.trailer.cli.command.policy

import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.io.service.PolicyService
import tech.coner.trailer.render.view.PolicyCollectionViewRenderer

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
    private val view: PolicyCollectionViewRenderer by instance(Format.TEXT)

    override suspend fun coRun() = diContext.use {
        val policies = service.list()
        echo(view(policies))
    }
}