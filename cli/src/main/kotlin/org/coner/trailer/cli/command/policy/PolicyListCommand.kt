package org.coner.trailer.cli.command.policy

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import org.coner.trailer.cli.view.PolicyView
import org.coner.trailer.io.service.PolicyService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class PolicyListCommand(di: DI) : CliktCommand(
    name = "list",
    help = "List policies"
), DIAware {

    override val di by findOrSetObject { di }

    private val service: PolicyService by instance()
    private val view: PolicyView by instance()

    override fun run() {
        val policies = service.list()
        policies.forEach { echo(view.render(it)) }
    }
}