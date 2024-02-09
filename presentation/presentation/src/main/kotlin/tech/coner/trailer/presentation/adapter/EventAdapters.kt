package tech.coner.trailer.presentation.adapter

import tech.coner.trailer.Event
import tech.coner.trailer.presentation.library.adapter.Adapter
import tech.coner.trailer.presentation.library.adapter.ListAdapter
import tech.coner.trailer.presentation.library.adapter.StringFieldAdapter
import tech.coner.trailer.presentation.model.EventDetailModel
import tech.coner.trailer.presentation.model.EventDetailCollectionModel

class EventIdStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Event> {
    override operator fun invoke(model: Event): String {
        return model.id.toString()
    }
}
class EventNameStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Event> {
    override operator fun invoke(model: Event): String {
        return model.name
    }
}
class EventDateStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Event> {
    override operator fun invoke(model: Event): String {
        return model.date.toString()
    }
}
class EventLifecycleStringFieldAdapter : tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Event> {
    override operator fun invoke(model: Event): String {
        return model.lifecycle.name.lowercase()
    }
}
class EventCrispyFishEventControlFileStringFieldAdapter :
    tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Event> {
    override operator fun invoke(model: Event): String {
        return model.crispyFish?.eventControlFile?.toString() ?: ""
    }
}
class EventCrispyFishClassDefinitionFileStringFieldAdapter :
    tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Event> {
    override operator fun invoke(model: Event): String {
        return model.crispyFish?.classDefinitionFile?.toString() ?: ""
    }
}
class EventMotorsportRegIdStringFieldAdapter :
    tech.coner.trailer.presentation.library.adapter.StringFieldAdapter<Event> {
    override operator fun invoke(model: Event): String {
        return model.motorsportReg?.id ?: ""
    }
}

class EventDetailModelAdapter(
    val id: EventIdStringFieldAdapter,
    val name: EventNameStringFieldAdapter,
    val date: EventDateStringFieldAdapter,
    val lifecycle: EventLifecycleStringFieldAdapter,
    val crispyFishEventControlFile: EventCrispyFishEventControlFileStringFieldAdapter,
    val crispyFishClassDefinitionFile: EventCrispyFishClassDefinitionFileStringFieldAdapter,
    val crispyFishPeopleMap: EventCrispyFishPeopleMapModelAdapter,
    val motorsportRegId: EventMotorsportRegIdStringFieldAdapter,
    val policyId: PolicyIdStringFieldAdapter,
    val policyName: PolicyNameStringFieldAdapter,
) : tech.coner.trailer.presentation.library.adapter.Adapter<Event, EventDetailModel> {
    override fun invoke(model: Event): EventDetailModel {
        return EventDetailModel(
            event = model,
            adapter = this
        )
    }
}

class EventCrispyFishPeopleMapModelAdapter(
    val signage: SignageStringFieldAdapter,
    val personId: PersonIdStringFieldAdapter,
    val personName: PersonFullNameStringFieldAdapter
) : tech.coner.trailer.presentation.library.adapter.ListAdapter<Event, EventDetailModel.CrispyFishPeopleMapModel> {
    override fun invoke(model: Event): List<EventDetailModel.CrispyFishPeopleMapModel> {
        return model.crispyFish?.peopleMap
            ?.map {
                EventDetailModel.CrispyFishPeopleMapModel(
                    event = model,
                    entry = it,
                    adapter = this
                )
            }
            ?: emptyList()
    }
}

class EventDetailCollectionModelAdapter(
    private val eventDetailAdapter: EventDetailModelAdapter
) : tech.coner.trailer.presentation.library.adapter.Adapter<Collection<Event>, EventDetailCollectionModel> {
    override fun invoke(model: Collection<Event>): EventDetailCollectionModel {
        return EventDetailCollectionModel(
            items = model.map(eventDetailAdapter::invoke)
        )
    }
}
