package tech.coner.trailer.presentation.model

import tech.coner.trailer.Event
import tech.coner.trailer.Person
import tech.coner.trailer.presentation.adapter.*
import tech.coner.trailer.presentation.library.model.Model

class EventDetailModel(
    private val event: Event,
    private val adapter: EventDetailModelAdapter
) : Model {
    val id
        get() = adapter.id(event)
    val name
        get() = adapter.name(event)
    val date
        get() = adapter.date(event)
    val lifecycle
        get() = adapter.lifecycle(event)
    val crispyFishEventControlFile
        get() = adapter.crispyFishEventControlFile(event)
    val crispyFishClassDefinitionFile
        get() = adapter.crispyFishClassDefinitionFile(event)
    val crispyFishPeopleMap
        get() = adapter.crispyFishPeopleMap(event)
    val motorsportRegId
        get() = adapter.motorsportRegId(event)
    val policyId
        get() = adapter.policyId(event.policy)
    val policyName
        get() = adapter.policyName(event.policy)

    class CrispyFishPeopleMapModel(
        private val event: Event,
        private val entry: Map.Entry<Event.CrispyFishMetadata.PeopleMapKey, Person>,
        private val adapter: EventCrispyFishPeopleMapModelAdapter
    ) : Model {
        val signage
            get() = adapter.signage(entry.key.signage, event.policy)
        val personId
            get() = adapter.personId(entry.value)
        val personName
            get() = adapter.personName(entry.value)


    }
}