package tech.coner.trailer.cli.command.policy

import com.github.ajalt.clikt.core.CliktCommand
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.view.PolicyView
import tech.coner.trailer.io.service.PolicyService

class PolicyListCommand(
    override val di: DI,
    private val global: GlobalModel
    ) : CliktCommand(
    name = "list",
    help = "List policies"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: PolicyService by instance()
    private val view: PolicyView by instance()

    override fun run() = diContext.use {
        val policies = service.list()
        policies.forEach { echo(view.render(it)) }
    }
}