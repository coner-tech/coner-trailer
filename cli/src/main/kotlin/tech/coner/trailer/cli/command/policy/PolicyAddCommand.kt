package tech.coner.trailer.cli.command.policy

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import tech.coner.trailer.Policy
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.view.PolicyView
import tech.coner.trailer.eventresults.FinalScoreStyle
import tech.coner.trailer.eventresults.PaxTimeStyle
import tech.coner.trailer.io.service.ClubService
import tech.coner.trailer.io.service.PolicyService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import java.util.*

class PolicyAddCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
    name = "add",
    help = "Add a policy"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }
    private val clubService: ClubService by instance()
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
    private val authoritativeParticipantDataSource: Policy.DataSource by option()
        .choice(
            "crispy-fish" to Policy.DataSource.CrispyFish
        )
        .default(Policy.DataSource.CrispyFish)
    private val authoritativeRunDataSource: Policy.DataSource by option()
        .choice(
            "crispy-fish" to Policy.DataSource.CrispyFish
        )
        .default(Policy.DataSource.CrispyFish)

    override fun run() = diContext.use {
        val create = Policy(
            id = id,
            club = clubService.get(),
            name = name,
            conePenaltySeconds = conePenaltySeconds,
            paxTimeStyle = paxTimeStyle,
            finalScoreStyle = finalScoreStyle,
            authoritativeParticipantDataSource = authoritativeParticipantDataSource,
            authoritativeRunDataSource = authoritativeRunDataSource
        )
        service.create(create)
        echo(view.render(create))
    }
}