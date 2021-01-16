package org.coner.trailer.io.service

import org.coner.trailer.Event
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.snoozle.EventResource
import org.coner.trailer.datasource.snoozle.entity.EventEntity
import org.coner.trailer.io.constraint.EventDeleteConstraints
import org.coner.trailer.io.constraint.EventPersistConstraints
import org.coner.trailer.io.mapper.EventMapper
import org.coner.trailer.io.verification.EventCrispyFishPersonMapVerifier
import java.util.*
import kotlin.streams.toList

class EventService(
    private val resource: EventResource,
    private val mapper: EventMapper,
    private val persistConstraints: EventPersistConstraints,
    private val deleteConstraints: EventDeleteConstraints,
    private val eventCrispyFishPersonMapVerifier: EventCrispyFishPersonMapVerifier
) {

    fun create(
        create: Event
    ) {
        persistConstraints.assess(create)
        resource.create(mapper.toSnoozle(create))
    }

    fun findById(id: UUID): Event {
        val key = EventEntity.Key(id = id)
        return mapper.toCore(resource.read(key))
    }

    fun findByName(name: String): Event {
        return resource.stream()
                .filter { it.name == name }
                .map(mapper::toCore)
                .findFirst()
                .orElseThrow { NotFoundException("No Event found with name: $name") }
    }

    fun list(): List<Event> {
        return resource.stream()
                .map(mapper::toCore)
                .toList()
    }

    /**
     * Persist an updated Event.
     *
     * @param[update] Event to persist
     * @param[context] CrispyFishEventMappingContext the full mapping context for the event. Only required when lifecycle >= ACTIVE
     * @param[eventCrispyFishPersonMapVerifierCallback] Failure callback for crispy fish force person verification
     */
    fun update(
        update: Event,
        context: CrispyFishEventMappingContext?,
        eventCrispyFishPersonMapVerifierCallback: EventCrispyFishPersonMapVerifier.Callback?
    ) {
        persistConstraints.assess(update)
        val doCrispyFishForceVerification = when (update.lifecycle) {
            Event.Lifecycle.CREATE, Event.Lifecycle.PRE -> false
            Event.Lifecycle.ACTIVE, Event.Lifecycle.POST, Event.Lifecycle.FINAL -> true
        }
        if (doCrispyFishForceVerification) {
            update.crispyFish?.also {
                eventCrispyFishPersonMapVerifier.verify(
                    context = requireNotNull(context) { "Must provide context for events with crispy fish metadata" },
                    peopleMap = it.peopleMap,
                    callback = eventCrispyFishPersonMapVerifierCallback
                )
            }
        }
        resource.update(mapper.toSnoozle(update))
    }

    fun delete(delete: Event) {
        deleteConstraints.assess(delete)
        resource.delete(mapper.toSnoozle(delete))
    }
}