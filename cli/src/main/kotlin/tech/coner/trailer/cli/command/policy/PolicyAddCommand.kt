package tech.coner.trailer.cli.command.policy

import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.Policy
import tech.coner.trailer.SignageStyle
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.handle
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.eventresults.FinalScoreStyle
import tech.coner.trailer.eventresults.PaxTimeStyle
import tech.coner.trailer.eventresults.StandardEventResultsTypes
import tech.coner.trailer.io.constraint.PolicyPersistConstraints
import tech.coner.trailer.io.service.ClubService
import tech.coner.trailer.io.service.PolicyService
import tech.coner.trailer.presentation.adapter.Adapter
import tech.coner.trailer.presentation.model.PolicyModel
import tech.coner.trailer.presentation.text.view.TextView
import java.util.*

class PolicyAddCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "add",
    help = "Add a policy"
) {

    override val diContext = diContextDataSession()
    private val constraints: PolicyPersistConstraints by instance()
    private val clubService: ClubService by instance()
    private val service: PolicyService by instance()
    private val adapter: Adapter<Policy, PolicyModel> by instance()
    private val textWidget: TextView<PolicyModel> by instance()

    private val id: UUID? by option(hidden = true)
        .convert { toUuid(it) }
    private val name: String by option()
        .required()
        .validate { id?.also { id -> handle(constraints.hasUniqueName(id to it)) } }
    private val conePenaltySeconds: Int by option()
        .int()
        .required()
        .validate { if (it < 0) fail("Must be greater than zero") }
    private val paxTimeStyle: PaxTimeStyle by option()
        .choice(PaxTimeStyle.values().associateBy { it.name.lowercase() })
        .default(PaxTimeStyle.FAIR)
    private val finalScoreStyle: FinalScoreStyle by option()
        .choice(FinalScoreStyle.values().associateBy { it.name.lowercase() })
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
    private val topTimesEventResultsMethod: EventResultsType by option()
        .choice(
            "pax" to StandardEventResultsTypes.pax,
            "raw" to StandardEventResultsTypes.raw
        )
        .default(StandardEventResultsTypes.pax)

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val create = Policy(
            id = id ?: UUID.randomUUID(),
            club = clubService.get(),
            name = name,
            conePenaltySeconds = conePenaltySeconds,
            paxTimeStyle = paxTimeStyle,
            finalScoreStyle = finalScoreStyle,
            authoritativeParticipantDataSource = authoritativeParticipantDataSource,
            authoritativeRunDataSource = authoritativeRunDataSource,
            topTimesEventResultsMethod = topTimesEventResultsMethod,
            signageStyle = SignageStyle.CLASSING_NUMBER
        )
        service.create(create)
        echo(textWidget(adapter(create)))
    }
}