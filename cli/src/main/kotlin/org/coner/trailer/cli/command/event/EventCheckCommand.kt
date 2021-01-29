package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import org.coner.crispyfish.model.Registration
import org.coner.trailer.Event
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.CrispyFishRegistrationTableView
import org.coner.trailer.cli.view.PeopleMapKeyTableView
import org.coner.trailer.io.service.CrispyFishEventMappingContextService
import org.coner.trailer.io.service.EventService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*
import kotlin.io.path.ExperimentalPathApi

@ExperimentalPathApi
class EventCheckCommand(di: DI) : CliktCommand(
    name = "check",
    help = "Check an event and report any problems"
), DIAware {

    override val di: DI by findOrSetObject { di }

    private val service: EventService by instance()
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService by instance()
    private val registrationTableView: CrispyFishRegistrationTableView by instance()
    private val peopleMapKeyTableView: PeopleMapKeyTableView by instance()

    private val id: UUID by argument().convert { toUuid(it) }

    override fun run() {
        val check = service.findById(id)
        service.check(check, )
        val crispyFish = check.crispyFish
        if (crispyFish != null) {
            val context = crispyFishEventMappingContextService.load(crispyFish)
            val unmappedClubMemberIdNullRegistrations = mutableListOf<Registration>()
            val unmappedClubMemberIdNotFoundRegistrations = mutableListOf<Registration>()
            val unmappedClubMemberIdAmbiguousRegistrations = mutableListOf<Registration>()
            val unmappedClubMemberIdMatchButNameMismatchRegistrations = mutableListOf<Registration>()
            val unmappedExactMatchRegistrations = mutableListOf<Registration>()
            val unusedPeopleMapKeys = mutableListOf<Event.CrispyFishMetadata.PeopleMapKey>()

            if (unmappedClubMemberIdNullRegistrations.isNotEmpty()) {
                echo("Found unmapped registration(s) with club member ID null:")
                echo(registrationTableView.render(unmappedClubMemberIdNullRegistrations))
            }
            if (unmappedClubMemberIdNotFoundRegistrations.isNotEmpty()) {
                echo("Found unmapped registration(s) with club member ID not found:")
                echo(registrationTableView.render(unmappedClubMemberIdNotFoundRegistrations))
            }
            if (unmappedClubMemberIdAmbiguousRegistrations.isNotEmpty()) {
                echo("Found unmapped registration(s) with club member ID ambiguous:")
                echo(registrationTableView.render(unmappedClubMemberIdAmbiguousRegistrations))
            }
            if (unmappedClubMemberIdMatchButNameMismatchRegistrations.isNotEmpty()) {
                echo("Found unmapped registration(s) with club member ID match but name mismatch:")
                echo(registrationTableView.render(unmappedClubMemberIdMatchButNameMismatchRegistrations))
            }
            if (unmappedExactMatchRegistrations.isNotEmpty()) {
                echo("Found unmapped registration(s) with exact matching people:")
                echo(registrationTableView.render(unmappedExactMatchRegistrations))
            }
            if (unusedPeopleMapKeys.isNotEmpty()) {
                echo("Found unused people map keys:")
                echo(peopleMapKeyTableView.render(unusedPeopleMapKeys))
            }
        }
    }
}