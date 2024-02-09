package tech.coner.trailer.app.admin.command.event.crispyfish

import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import java.util.UUID
import kotlinx.coroutines.CoroutineScope
import org.kodein.di.DI
import org.kodein.di.instance
import tech.coner.trailer.Classing
import tech.coner.trailer.Event
import tech.coner.trailer.app.admin.command.BaseCommand
import tech.coner.trailer.app.admin.command.GlobalModel
import tech.coner.trailer.app.admin.di.use
import tech.coner.trailer.app.admin.util.clikt.toUuid
import tech.coner.trailer.io.service.CrispyFishClassService
import tech.coner.trailer.io.service.EventService
import tech.coner.trailer.io.service.PersonService
import tech.coner.trailer.presentation.library.adapter.Adapter
import tech.coner.trailer.presentation.model.EventDetailModel
import tech.coner.trailer.presentation.text.view.TextView

class EventCrispyFishPersonMapRemoveCommand(
    di: DI,
    global: GlobalModel
) : BaseCommand(
    di = di,
    global = global,
    name = "person-map-remove",
    help = "Remove a Crispy Fish Person Map entry"
) {

    override val diContext = diContextDataSession()
    private val service: EventService by instance()
    private val crispyFishClassService: CrispyFishClassService by instance()
    private val personService: PersonService by instance()
    private val adapter: tech.coner.trailer.presentation.library.adapter.Adapter<Event, EventDetailModel> by instance()
    private val view: TextView<EventDetailModel> by instance()

    private val id: UUID by argument().convert { toUuid(it) }
    private val group: String? by option()
    private val handicap: String by option().required()
    private val number: String by option().required()
    private val firstName: String by option().required()
    private val lastName: String by option().required()
    private val personId: UUID by option().convert { toUuid(it) }.required()

    override suspend fun CoroutineScope.coRun() = diContext.use {
        val event = service.findByKey(id).getOrThrow()
        val crispyFish = checkNotNull(event.crispyFish) {
            "Event must have crispy fish defined already"
        }
        val allClassesByAbbreviation = crispyFishClassService.loadAllByAbbreviation(crispyFish.classDefinitionFile)
        val classing = Classing(
            group = allClassesByAbbreviation[group],
            handicap = allClassesByAbbreviation[handicap] ?: error("No class found for handicap: $handicap")
        )
        val person = personService.findById(personId)
        val key = Event.CrispyFishMetadata.PeopleMapKey(
            classing = classing,
            number = number,
            firstName = firstName,
            lastName = lastName
        )
        val setCrispyFish = crispyFish.copy(
            peopleMap = crispyFish.peopleMap.toMutableMap().apply {
                val removed = remove(key)
                if (removed == null) {
                    echo("Key not found")
                    throw Abort()
                }
                if (removed.id != person.id) {
                    echo("Key was assigned to a different person ID. Aborting!")
                    throw Abort()
                }
            }
        )
        val set = event.copy(crispyFish = setCrispyFish)
        service.update(set)
        echo(view(adapter(set)))
    }
}