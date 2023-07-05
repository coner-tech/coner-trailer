package tech.coner.trailer.cli.command.policy

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.di.render.Format
import tech.coner.trailer.eventresults.FinalScoreStyle
import tech.coner.trailer.eventresults.PaxTimeStyle
import tech.coner.trailer.io.service.PolicyService
import tech.coner.trailer.render.view.PolicyViewRenderer
import java.util.*

class PolicySetCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "set",
    help = "Set a Policy"
) {

    override val diContext = diContextDataSession()
    private val service: PolicyService by instance()
    private val view: PolicyViewRenderer by instance(Format.TEXT)

    private val id: UUID by argument().convert { toUuid(it) }
    private val name: String? by option()
    private val conePenaltySeconds: Int? by option()
        .int()
        .validate { if (it < 0) fail("Must be greater than zero") }
    private val paxTimeStyle: PaxTimeStyle? by option()
        .choice(PaxTimeStyle.values().associateBy { it.name.toLowerCase() })
    private val finalScoreStyle: FinalScoreStyle? by option()
        .choice(FinalScoreStyle.values().associateBy { it.name.toLowerCase() })

    override suspend fun coRun() = diContext.use {
        val set = service.findById(id).let { it.copy(
            name = name ?: it.name,
            conePenaltySeconds = conePenaltySeconds ?: it.conePenaltySeconds,
            paxTimeStyle = paxTimeStyle ?: it.paxTimeStyle,
            finalScoreStyle = finalScoreStyle ?: it.finalScoreStyle
        ) }
        service.update(set)
        echo(view(set))
    }
}