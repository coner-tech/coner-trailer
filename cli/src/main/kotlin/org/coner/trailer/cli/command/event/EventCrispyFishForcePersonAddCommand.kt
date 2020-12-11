package org.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import org.coner.trailer.Participant
import org.coner.trailer.cli.command.grouping.GroupingOption
import org.coner.trailer.cli.command.grouping.groupingOption
import org.coner.trailer.cli.util.clikt.toUuid
import org.coner.trailer.cli.view.EventView
import org.coner.trailer.io.service.CrispyFishGroupingService
import org.coner.trailer.io.service.EventService
import org.coner.trailer.io.service.PersonService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*

class EventCrispyFishForcePersonAddCommand(
    di: DI
) : CliktCommand(
    name = "crispy-fish-force-person-add",
    help = "Add a Crispy Fish Force Person entry"
), DIAware {

    override val di: DI by findOrSetObject { di }

    private val service: EventService by instance()
    private val groupingService: CrispyFishGroupingService by instance()
    private val personService: PersonService by instance()
    private val view: EventView by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    private val grouping: GroupingOption by groupingOption().required()
    private val number: String by option().required()
    private val personId: UUID by option().convert { toUuid(it) }.required()

    override fun run() {
        val event = service.findById(id)
        val crispyFish = checkNotNull(event.crispyFish) {
            "Event must have crispy fish defined already"
        }
        val signage = Participant.Signage(
            grouping = when (val grouping = grouping) {
                is GroupingOption.Singular -> groupingService.findSingular(
                    crispyFish = crispyFish,
                    abbreviation = grouping.abbreviationSingular
                )
                is GroupingOption.Paired -> groupingService.findPaired(
                    crispyFish = crispyFish,
                    abbreviations = grouping.abbreviationsPaired
                )
            },
            number = number
        )
        val person = personService.findById(personId)
        val set = event.copy(
            crispyFish = crispyFish.copy(
                forcePeople = crispyFish.forcePeople.toMutableMap().apply {
                    put(signage, person)
                }
            )
        )
        service.update(set)
        echo(view.render(set))
    }
}