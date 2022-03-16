package org.coner.trailer.cli.command.policy

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import org.coner.trailer.cli.command.GlobalModel
import org.coner.trailer.cli.di.use
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.PolicyView
import org.coner.trailer.io.service.PolicyService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import java.util.*

class PolicyGetCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
    name = "get",
    help = "Get a policy"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val service: PolicyService by instance()
    private val view: PolicyView by instance()

    private val find: Find by mutuallyExclusiveOptions(
        option("--id").convert { Find.ById(id = toUuid(it)) },
        option("--name").convert { Find.ByName(name = it) }
    ).required()
    sealed class Find {
        class ById(val id: UUID) : Find()
        class ByName(val name: String) : Find()
    }

    override fun run() = diContext.use {
        val policy = when (val find = find) {
            is Find.ById -> service.findById(find.id)
            is Find.ByName -> service.findByName(find.name)
        }
        echo(view.render(policy))
    }
}