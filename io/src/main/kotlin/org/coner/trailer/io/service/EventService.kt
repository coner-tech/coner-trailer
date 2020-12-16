package org.coner.trailer.io.service

import org.coner.trailer.Event
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.snoozle.EventResource
import org.coner.trailer.datasource.snoozle.entity.EventEntity
import org.coner.trailer.io.constraint.EventDeleteConstraints
import org.coner.trailer.io.constraint.EventPersistConstraints
import org.coner.trailer.io.mapper.EventMapper
import org.coner.trailer.io.verification.EventCrispyFishForcePersonVerification
import java.util.*
import kotlin.streams.toList

class EventService(
    private val resource: EventResource,
    private val mapper: EventMapper,
    private val persistConstraints: EventPersistConstraints,
    private val deleteConstraints: EventDeleteConstraints,
    private val eventCrispyFishForcePersonVerification: EventCrispyFishForcePersonVerification
) {

    fun create(
        create: Event,
        context: CrispyFishEventMappingContext?,
        eventCrispyFishForcePersonVerificationFailureCallback: EventCrispyFishForcePersonVerification.FailureCallback?
    ) {
        persistConstraints.assess(create)
        if (create.crispyFish != null) {
            eventCrispyFishForcePersonVerification.verifyRegistrations(
                context = requireNotNull(context) { "Must provide context for events with crispy fish metadata" },
                failureCallback = eventCrispyFishForcePersonVerificationFailureCallback
            )
        }
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

    fun update(update: Event) {
        persistConstraints.assess(update)
        resource.update(mapper.toSnoozle(update))
    }

    fun delete(delete: Event) {
        deleteConstraints.assess(delete)
        resource.delete(mapper.toSnoozle(delete))
    }
}