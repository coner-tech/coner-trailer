package tech.coner.trailer.cli.command.event

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import tech.coner.trailer.cli.command.BaseCommand
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.view.CrispyFishRegistrationTableView
import tech.coner.trailer.cli.view.PeopleMapKeyTableView
import tech.coner.trailer.io.service.CrispyFishEventMappingContextService
import tech.coner.trailer.io.service.EventService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.factory
import org.kodein.di.instance
import tech.coner.trailer.Policy
import tech.coner.trailer.render.text.TextRunsRenderer
import java.util.*

class EventCheckCommand(
    override val di: DI,
    global: GlobalModel
) : BaseCommand(
    global = global,
    name = "check",
    help = "Check an event and report any problems"
), DIAware {

    override val diContext = diContextDataSession()
    private val service: EventService by instance()
    private val registrationTableView: CrispyFishRegistrationTableView by instance()
    private val peopleMapKeyTableView: PeopleMapKeyTableView by instance()
    private val textRunsRenderer: TextRunsRenderer by instance()

    private val id: UUID by argument().convert { toUuid(it) }

    override fun run() = diContext.use {
        val check = service.findById(id)
        val result = service.check(check)
        if (result.unmappable.isNotEmpty()) {
            echo("Found unmappable registration(s):")
            echo(registrationTableView.render(result.unmappable))
        }
        if (result.unmappedMotorsportRegPersonMatches.isNotEmpty()) {
            echo("Found unmapped registration(s) with cross-reference to person via motorsportreg assignments:")
            echo(registrationTableView.render(result.unmappedMotorsportRegPersonMatches.map { it.first }))
        }
        if (result.unmappedClubMemberIdNullRegistrations.isNotEmpty()) {
            echo("Found unmapped registration(s) with club member ID null:")
            echo(registrationTableView.render(result.unmappedClubMemberIdNullRegistrations))
        }
        if (result.unmappedClubMemberIdNotFoundRegistrations.isNotEmpty()) {
            echo("Found unmapped registration(s) with club member ID not found:")
            echo(registrationTableView.render(result.unmappedClubMemberIdNotFoundRegistrations))
        }
        if (result.unmappedClubMemberIdAmbiguousRegistrations.isNotEmpty()) {
            echo("Found unmapped registration(s) with club member ID ambiguous:")
            echo(registrationTableView.render(result.unmappedClubMemberIdAmbiguousRegistrations))
        }
        if (result.unmappedClubMemberIdMatchButNameMismatchRegistrations.isNotEmpty()) {
            echo("Found unmapped registration(s) with club member ID match but name mismatch:")
            echo(registrationTableView.render(result.unmappedClubMemberIdMatchButNameMismatchRegistrations))
        }
        if (result.unmappedExactMatchRegistrations.isNotEmpty()) {
            echo("Found unmapped registration(s) with exact matching people:")
            echo(registrationTableView.render(result.unmappedExactMatchRegistrations))
        }
        if (result.unusedPeopleMapKeys.isNotEmpty()) {
            echo("Found unused people map keys:")
            echo(peopleMapKeyTableView.render(result.unusedPeopleMapKeys))
        }
        if (result.runsWithInvalidSignage.isNotEmpty()) {
            echo("Found runs with invalid signage:")
            echo(textRunsRenderer.render(result.runsWithInvalidSignage))
        }
    }
}