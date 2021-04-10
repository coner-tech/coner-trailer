package org.coner.trailer.cli.command.policy

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import org.coner.trailer.Policy
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.PolicyView
import org.coner.trailer.eventresults.FinalScoreStyle
import org.coner.trailer.eventresults.PaxTimeStyle
import org.coner.trailer.io.service.PolicyService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*

class PolicyAddCommand(di: DI) : CliktCommand(
    name = "add",
    help = "Add a policy"
), DIAware {

    override val di: DI by findOrSetObject { di }

    private val service: PolicyService by instance()
    private val view: PolicyView by instance()

    private val id: UUID by option(hidden = true)
        .convert { toUuid(it) }
        .default(UUID.randomUUID())
    private val name: String by option()
        .required()
    private val conePenaltySeconds: Int by option()
        .int()
        .required()
        .validate { if (it < 0) fail("Must be greater than zero") }
    private val paxTimeStyle: PaxTimeStyle by option()
        .choice(PaxTimeStyle.values().associateBy { it.name.toLowerCase() })
        .default(PaxTimeStyle.FAIR)
    private val finalScoreStyle: FinalScoreStyle by option()
        .choice(FinalScoreStyle.values().associateBy { it.name.toLowerCase() })
        .required()

    override fun run() {
        val create = Policy(
            id = id,
            name = name,
            conePenaltySeconds = conePenaltySeconds,
            paxTimeStyle = paxTimeStyle,
            finalScoreStyle = finalScoreStyle
        )
        service.create(create)
        echo(view.render(create))
    }
}