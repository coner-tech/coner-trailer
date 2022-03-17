package tech.coner.trailer.io.service

import tech.coner.trailer.Event
import tech.coner.trailer.Person
import tech.coner.trailer.Policy
import tech.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import tech.coner.trailer.datasource.snoozle.EventResource
import tech.coner.trailer.datasource.snoozle.entity.EventEntity
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.constraint.EventDeleteConstraints
import tech.coner.trailer.io.constraint.EventPersistConstraints
import tech.coner.trailer.io.mapper.EventMapper
import tech.coner.trailer.io.verification.EventCrispyFishPersonMapVerifier
import tech.coner.crispyfish.model.Registration
import java.nio.file.Path
import java.time.LocalDate
import java.util.*

class EventService(
    private val dbConfig: DatabaseConfiguration,
    private val resource: EventResource,
    private val mapper: EventMapper,
    private val persistConstraints: EventPersistConstraints,
    private val deleteConstraints: EventDeleteConstraints,
    private val eventCrispyFishPersonMapVerifier: EventCrispyFishPersonMapVerifier,
) {

    fun create(
        id: UUID? = null,
        name: String,
        date: LocalDate,
        crispyFishEventControlFile: Path,
        crispyFishClassDefinitionFile: Path,
        motorsportRegEventId: String?,
        policy: Policy
    ): Event {
        val create = Event(
            id = id ?: UUID.randomUUID(),
            name = name,
            date = date,
            lifecycle = Event.Lifecycle.CREATE,
            crispyFish = Event.CrispyFishMetadata(
                eventControlFile = dbConfig.asRelativeToCrispyFishDatabase(crispyFishEventControlFile),
                classDefinitionFile = dbConfig.asRelativeToCrispyFishDatabase(crispyFishClassDefinitionFile),
                peopleMap = emptyMap() // out of scope for add command
            ),
            motorsportReg = motorsportRegEventId?.let { Event.MotorsportRegMetadata(
                id = it
            ) },
            policy = policy
        )
        persistConstraints.assess(create)
        resource.create(mapper.toSnoozle(create))
        return create
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

                override fun onUnmappableClassing(registration: Registration) {
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
     */
    fun update(
        update: Event
    ) {
        persistConstraints.assess(update)
        val allowUnmappedCrispyFishPeople = when (update.lifecycle) {
            Event.Lifecycle.CREATE, Event.Lifecycle.PRE -> true
            Event.Lifecycle.ACTIVE, Event.Lifecycle.POST, Event.Lifecycle.FINAL -> false
        }
        if (!allowUnmappedCrispyFishPeople) {
            eventCrispyFishPersonMapVerifier.verify(
                event = update,
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