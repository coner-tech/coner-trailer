package tech.coner.trailer.io.service

import kotlinx.coroutines.CoroutineScope
import tech.coner.crispyfish.model.Registration
import tech.coner.trailer.Event
import tech.coner.trailer.EventId
import tech.coner.trailer.Person
import tech.coner.trailer.datasource.snoozle.EventResource
import tech.coner.trailer.datasource.snoozle.entity.EventEntity
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.constraint.EventDeleteConstraints
import tech.coner.trailer.io.constraint.EventPersistConstraints
import tech.coner.trailer.io.mapper.EventMapper
import tech.coner.trailer.io.payload.CreateEventPayload
import tech.coner.trailer.io.payload.EventHealthCheckOutcome
import tech.coner.trailer.io.util.runSuspendCatching
import tech.coner.trailer.io.verifier.EventCrispyFishPersonMapVerifier
import tech.coner.trailer.io.verifier.RunWithInvalidSignageVerifier
import java.util.*

class EventService(
    coroutineScope: CoroutineScope,
    private val dbConfig: DatabaseConfiguration,
    private val resource: EventResource,
    private val mapper: EventMapper,
    private val persistConstraints: EventPersistConstraints,
    private val deleteConstraints: EventDeleteConstraints,
    private val eventCrispyFishPersonMapVerifier: EventCrispyFishPersonMapVerifier,
    private val runWithInvalidSignageVerifier: RunWithInvalidSignageVerifier,
    private val crispyFishEventMappingContextService: CrispyFishEventMappingContextService,
    private val runService: RunService,
    private val participantService: ParticipantService
) : CrudService<CreateEventPayload, EventId, Event>,
    CoroutineScope by coroutineScope {

    override suspend fun create(payload: CreateEventPayload): Result<Event> = runSuspendCatching {
        val create = Event(
            id = payload.id ?: UUID.randomUUID(),
            name = payload.name,
            date = payload.date,
            lifecycle = Event.Lifecycle.CREATE,
            crispyFish = Event.CrispyFishMetadata(
                eventControlFile = dbConfig.asRelativeToCrispyFishDatabase(payload.crispyFishEventControlFile),
                classDefinitionFile = dbConfig.asRelativeToCrispyFishDatabase(payload.crispyFishClassDefinitionFile),
                peopleMap = emptyMap() // out of scope for add command
            ),
            motorsportReg = payload.motorsportRegEventId?.let { Event.MotorsportRegMetadata(
                id = it
            ) },
            policy = payload.policy
        )
        persistConstraints.assess(create)
        resource.create(mapper.toSnoozle(create))
        create
    }

    override suspend fun findByKey(key: EventId): Result<Event> = runSuspendCatching {
        mapper.toCore(resource.read(EventEntity.Key(id = key)))
    }

    suspend fun findByName(name: String): Result<Event> = runSuspendCatching {
        resource.stream()
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

    suspend fun check(check: Event): EventHealthCheckOutcome {
        val unmappedMotorsportRegPersonMatches = mutableListOf<Pair<Registration, Pair<Event.CrispyFishMetadata.PeopleMapKey, Person>>>()
        val unmappable = mutableListOf<Registration>()
        val unmappedClubMemberIdNullRegistrations = mutableListOf<Registration>()
        val unmappedClubMemberIdNotFoundRegistrations = mutableListOf<Registration>()
        val unmappedClubMemberIdAmbiguousRegistrations = mutableListOf<Registration>()
        val unmappedClubMemberIdMatchButNameMismatchRegistrations = mutableListOf<Registration>()
        val unmappedExactMatchRegistrations = mutableListOf<Registration>()
        val unusedPeopleMapKeys = mutableListOf<Event.CrispyFishMetadata.PeopleMapKey>()
        val context = crispyFishEventMappingContextService.load(check, check.requireCrispyFish())
        val participants = participantService.list(check).getOrThrow()
        val runs = runService.list(check).getOrThrow()
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
        return EventHealthCheckOutcome(
            unmappable = unmappable,
            unmappedMotorsportRegPersonMatches = unmappedMotorsportRegPersonMatches,
            unmappedClubMemberIdNullRegistrations = unmappedClubMemberIdNullRegistrations,
            unmappedClubMemberIdNotFoundRegistrations = unmappedClubMemberIdNotFoundRegistrations,
            unmappedClubMemberIdAmbiguousRegistrations = unmappedClubMemberIdAmbiguousRegistrations,
            unmappedClubMemberIdMatchButNameMismatchRegistrations = unmappedClubMemberIdMatchButNameMismatchRegistrations,
            unmappedExactMatchRegistrations = unmappedExactMatchRegistrations,
            unusedPeopleMapKeys = unusedPeopleMapKeys,
            runsWithInvalidSignage = runWithInvalidSignageVerifier.verify(
                allRuns = runs,
                allParticipants = participants
            )
        )
    }

    /**
     * Persist an updated Event.
     *
     * @param[model] Event to persist
     */
    override suspend fun update(model: Event): Result<Event> = runSuspendCatching {
        persistConstraints.assess(model)
        val allowUnmappedCrispyFishPeople = when (model.lifecycle) {
            Event.Lifecycle.CREATE, Event.Lifecycle.PRE -> true
            Event.Lifecycle.ACTIVE, Event.Lifecycle.POST, Event.Lifecycle.FINAL -> false
        }
        if (!allowUnmappedCrispyFishPeople) {
            eventCrispyFishPersonMapVerifier.verify(
                event = model,
                context = crispyFishEventMappingContextService.load(model, model.requireCrispyFish()),
                callback = EventCrispyFishPersonMapVerifier.ThrowingCallback()
            )
        }
        resource.update(mapper.toSnoozle(model))
        model
    }

    override suspend fun delete(model: Event): Result<Unit> = runSuspendCatching {
        deleteConstraints.assess(model)
        resource.delete(mapper.toSnoozle(model))
    }
}