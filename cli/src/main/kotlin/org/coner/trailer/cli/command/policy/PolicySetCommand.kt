package org.coner.trailer.cli.command.policy

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.PolicyView
import org.coner.trailer.eventresults.FinalScoreStyle
import org.coner.trailer.eventresults.PaxTimeStyle
import org.coner.trailer.io.service.PolicyService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*

class PolicySetCommand(di: DI) : CliktCommand(
    name = "set",
    help = "Set a Policy"
), DIAware {

    override val di by findOrSetObject { di }

    private val service: PolicyService by instance()
    private val view: PolicyView by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    private val name: String? by option()
    private val conePenaltySeconds: Int? by option()
        .int()
        .validate { if (it < 0) fail("Must be greater than zero") }
    private val paxTimeStyle: PaxTimeStyle? by option()
        .choice(PaxTimeStyle.values().map { it.name.toLowerCase() to it }.toMap())
    private val finalScoreStyle: FinalScoreStyle? by option()
        .choice(FinalScoreStyle.values().map { it.name.toLowerCase() to it }.toMap())

    override fun run() {
        val set = service.findById(id).let { it.copy(
            name = name ?: it.name,
            conePenaltySeconds = conePenaltySeconds ?: it.conePenaltySeconds,
            paxTimeStyle = paxTimeStyle ?: it.paxTimeStyle,
            finalScoreStyle = finalScoreStyle ?: it.finalScoreStyle
        ) }
        service.update(set)
        echo(view.render(set))
    }
}