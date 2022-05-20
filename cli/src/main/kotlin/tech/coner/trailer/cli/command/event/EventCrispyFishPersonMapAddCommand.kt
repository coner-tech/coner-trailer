package tech.coner.trailer.cli.command.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import tech.coner.trailer.Classing
import tech.coner.trailer.Event
import tech.coner.trailer.cli.command.GlobalModel
import tech.coner.trailer.cli.di.use
import tech.coner.trailer.cli.util.clikt.toUuid
import tech.coner.trailer.cli.view.EventView
import tech.coner.trailer.io.service.CrispyFishClassService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.PersonService
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.diContext
import org.kodein.di.instance
import java.util.*

class EventCrispyFishPersonMapAddCommand(
    override val di: DI,
    private val global: GlobalModel
) : CliktCommand(
    name = "crispy-fish-person-map-add",
    help = "Add a Crispy Fish Person Map entry"
), DIAware {

    override val diContext = diContext { global.requireEnvironment().openDataSession() }

    private val service: EventService by instance()
    private val crispyFishClassService: CrispyFishClassService by instance()
    private val personService: PersonService by instance()
    private val view: EventView by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    private val group: String? by option()
    private val handicap: String by option().required()
    private val number: String by option().required()
    private val firstName: String by option().required()
    private val lastName: String by option().required()
    private val personId: UUID by option().convert { toUuid(it) }.required()

    override fun run() = diContext.use {
        val event = service.findById(id)
        val crispyFish = checkNotNull(event.crispyFish) {
            "Event must have crispy fish defined already"
        }
        val allClassesByAbbreviation = crispyFishClassService.loadAllByAbbreviation(crispyFish.classDefinitionFile)
        val classing = Classing(
            group = allClassesByAbbreviation[group],
            handicap = allClassesByAbbreviation[handicap] ?: error("No class found for handicap: $handicap")
        )
        val key = Event.CrispyFishMetadata.PeopleMapKey(
            classing = classing,
            number = number,
            firstName = firstName,
            lastName = lastName
        )
        val person = personService.findById(personId)
        val setCrispyFish = crispyFish.copy(
            peopleMap = crispyFish.peopleMap.toMutableMap().apply {
                put(key, person)
            }
        )
        val set = event.copy(crispyFish = setCrispyFish)
        service.update(set)
        echo(view.render(set))
    }
}