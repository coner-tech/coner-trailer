package org.coner.trailer.io.service

import tech.coner.crispyfish.model.Registration
import org.coner.trailer.Event
import org.coner.trailer.Person
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
    private val eventCrispyFishPersonMapVerifier: EventCrispyFishPersonMapVerifier,
    private val motorsportRegEventService: MotorsportRegEventService
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
            .sorted(compareBy(Event::date))
            .toList()
    }

    fun check(
        check: Event,
        context: CrispyFishEventMappingContext
    ): CheckResult {
        val unmappedMotorsportRegPersonMatches = mutableListOf<Pair<Registration, Pair<Event.CrispyFishMetadata.PeopleMapKey, Person>>>()
        val unmappable = mutableListOf<Registration>()
        val unmappedClubMemberIdNullRegistrations = mutableListOf<Registration>()
        val unmappedClubMemberIdNotFoundRegistrations = mutableListOf<Registration>()
        val unmappedClubMemberIdAmbiguousRegistrations = mutableListOf<Registration>()
        val unmappedClubMemberIdMatchButNameMismatchRegistrations = mutableListOf<Registration>()
        val unmappedExactMatchRegistrations = mutableListOf<Registration>()
        val unusedPeopleMapKeys = mutableListOf<Event.CrispyFishMetadata.PeopleMapKey>()
        eventCrispyFishPersonMapVerifier.verify(
            event = check,
            context = context,
            callback = object : EventCrispyFishPersonMapVerifier.Callback {
                override fun onMapped(
                    registration: Registration,
                    entry: Pair<Event.CrispyFishMetadata.PeopleMapKey, Person>
                ) {
                    // no-op
                }

                override fun onUnmappedMotorsportRegPersonExactMatch(
                    registration: Registration,
                    entry: Pair<Event.CrispyFishMetadata.PeopleMapKey, Person>
                ) {
                    unmappedMotorsportRegPersonMatches += registration to entry
                }

                override fun onUnmappableFirstNameNull(registration: Registration) {
                    unmappable += registration
                }

                override fun onUnmappableLastNameNull(registration: Registration) {
                    unmappable += registration
                }

                override fun onUnmappableGrouping(registration: Registration) {
                    unmappable += registration
                }

                override fun onUnmappableNumber(registration: Registration) {
                    unmappable += registration
                }

                override fun onUnmappedClubMemberIdNull(registration: Registration) {
                    unmappedClubMemberIdNullRegistrations += registration
                }

                override fun onUnmappedClubMemberIdNotFound(registration: Registration) {
                    unmappedClubMemberIdNotFoundRegistrations += registration
                }

                override fun onUnmappedClubMemberIdAmbiguous(
                    registration: Registration,
                    peopleWithClubMemberId: List<Person>
                ) {
                    unmappedClubMemberIdAmbiguousRegistrations += registration
                }

                override fun onUnmappedClubMemberIdMatchButNameMismatch(
                    registration: Registration,
                    person: Person
                ) {
                    unmappedClubMemberIdMatchButNameMismatchRegistrations += registration
                }

                override fun onUnmappedExactMatch(registration: Registration, person: Person) {
                    unmappedExactMatchRegistrations += registration
                }

                override fun onUnused(key: Event.CrispyFishMetadata.PeopleMapKey, person: Person) {
                    unusedPeopleMapKeys += key
                }
            }
        )
        return CheckResult(
            unmappable = unmappable,
            unmappedMotorsportRegPersonMatches = unmappedMotorsportRegPersonMatches,
            unmappedClubMemberIdNullRegistrations = unmappedClubMemberIdNullRegistrations,
            unmappedClubMemberIdNotFoundRegistrations = unmappedClubMemberIdNotFoundRegistrations,
            unmappedClubMemberIdAmbiguousRegistrations = unmappedClubMemberIdAmbiguousRegistrations,
            unmappedClubMemberIdMatchButNameMismatchRegistrations = unmappedClubMemberIdMatchButNameMismatchRegistrations,
            unmappedExactMatchRegistrations = unmappedExactMatchRegistrations,
            unusedPeopleMapKeys = unusedPeopleMapKeys
        )
    }

    class CheckResult(
        val unmappable: List<Registration>,
        val unmappedMotorsportRegPersonMatches: List<Pair<Registration, Pair<Event.CrispyFishMetadata.PeopleMapKey, Person>>>,
        val unmappedClubMemberIdNullRegistrations: List<Registration>,
        val unmappedClubMemberIdNotFoundRegistrations: List<Registration>,
        val unmappedClubMemberIdAmbiguousRegistrations: List<Registration>,
        val unmappedClubMemberIdMatchButNameMismatchRegistrations: List<Registration>,
        val unmappedExactMatchRegistrations: List<Registration>,
        val unusedPeopleMapKeys: List<Event.CrispyFishMetadata.PeopleMapKey>
    )

    /**
     * Persist an updated Event.
     *
     * @param[update] Event to persist
     * @param[context] CrispyFishEventMappingContext the full mapping context for the event. Only required when lifecycle >= ACTIVE
     */
    fun update(
        update: Event,
        context: CrispyFishEventMappingContext?
    ) {
        persistConstraints.assess(update)
        val allowUnmappedCrispyFishPeople = when (update.lifecycle) {
            Event.Lifecycle.CREATE, Event.Lifecycle.PRE -> true
            Event.Lifecycle.ACTIVE, Event.Lifecycle.POST, Event.Lifecycle.FINAL -> false
        }
        if (!allowUnmappedCrispyFishPeople) {
            eventCrispyFishPersonMapVerifier.verify(
                event = update,
                context = checkNotNull(context) { "crispy fish event mapping context is required" },
                callback = EventCrispyFishPersonMapVerifier.ThrowingCallback()
            )
        }
        resource.update(mapper.toSnoozle(update))
    }

    fun delete(delete: Event) {
        deleteConstraints.assess(delete)
        resource.delete(mapper.toSnoozle(delete))
    }
}