package org.coner.trailer.io.verification

import org.coner.crispyfish.model.Registration
import org.coner.trailer.Event
import org.coner.trailer.Person
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.io.service.MotorsportRegPeopleMapService
import org.coner.trailer.io.service.PersonService

class EventCrispyFishPersonMapVerifier(
    private val personService: PersonService,
    private val crispyFishParticipantMapper: CrispyFishParticipantMapper,
    private val motorsportRegPeopleMapService: MotorsportRegPeopleMapService
) {

    fun verify(
        event: Event,
        context: CrispyFishEventMappingContext,
        callback: Callback
    ) {
        val people = personService.list()
        val clubMemberIdToPeople: Map<String?, List<Person>> by lazy {
            people.groupBy { it.clubMemberId }
        }
        val msrPeopleMap: Map<Event.CrispyFishMetadata.PeopleMapKey, Person> by lazy {
            val peopleByMotorsportRegMemberId: Map<String, Person> = people.associateBy { it.motorsportReg?.memberId }
                .mapNotNull { entry -> entry.key?.let { key -> key to entry.value } }
                .toMap()
            motorsportRegPeopleMapService.assemble(
                event = event,
                peopleByMotorsportRegMemberId = peopleByMotorsportRegMemberId
        ) }
        val usedKeys = hashSetOf<Event.CrispyFishMetadata.PeopleMapKey>()
        for (registration in context.allRegistrations) {
            val signage = crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registration
            )
            val key = Event.CrispyFishMetadata.PeopleMapKey(
                signage = signage,
                firstName = registration.firstName,
                lastName = registration.lastName
            )
            val mappedPerson = event.crispyFish?.peopleMap?.get(key)
            if (mappedPerson != null) {
                // mapped
                usedKeys += key
                callback.onMapped(registration, key to mappedPerson)
            } else {
                handleUnmapped(
                    callback = callback,
                    registration = registration,
                    msrPeopleMap = msrPeopleMap,
                    clubMemberIdToPeople = clubMemberIdToPeople,
                    key = key
                )
            }
        }
        val unusedMappings = event.crispyFish?.peopleMap?.filter { !usedKeys.contains(it.key) } ?: emptyMap()
        for (unusedMapping in unusedMappings) {
            callback.onUnused(unusedMapping.key, unusedMapping.value)
        }
    }

    private fun handleUnmapped(
        key: Event.CrispyFishMetadata.PeopleMapKey,
        callback: Callback,
        registration: Registration,
        msrPeopleMap: Map<Event.CrispyFishMetadata.PeopleMapKey, Person>,
        clubMemberIdToPeople: Map<String?, List<Person>>
    ) {
        val person = msrPeopleMap[key]
        if (person != null) {
            callback.onUnmappedMotorsportRegPersonExactMatch(registration, key to person)
            return
        }
        val memberNumber = registration.memberNumber
        if (memberNumber.isNullOrEmpty()) {
            callback.onUnmappedClubMemberIdNull(registration)
            return
        }
        val peopleWithMemberId = clubMemberIdToPeople[registration.memberNumber]
        when {
            peopleWithMemberId == null -> callback.onUnmappedClubMemberIdNotFound(registration)
            peopleWithMemberId.size > 1 -> callback.onUnmappedClubMemberIdAmbiguous(registration, peopleWithMemberId)
            peopleWithMemberId.size == 1 -> {
                val personWithMemberId = peopleWithMemberId.single()
                if (registration.firstName == personWithMemberId.firstName
                    && registration.lastName == personWithMemberId.lastName) {
                    callback.onUnmappedExactMatch(registration, personWithMemberId)
                } else {
                    callback.onUnmappedClubMemberIdMatchButNameMismatch(registration, personWithMemberId)
                }
            }
            else -> throw IllegalStateException()
        }
    }

    interface Callback {
        fun onMapped(registration: Registration, entry: Pair<Event.CrispyFishMetadata.PeopleMapKey, Person>)
        fun onUnmappedMotorsportRegPersonExactMatch(registration: Registration, entry: Pair<Event.CrispyFishMetadata.PeopleMapKey, Person>)
        fun onUnmappedClubMemberIdNull(registration: Registration)
        fun onUnmappedClubMemberIdNotFound(registration: Registration)
        fun onUnmappedClubMemberIdAmbiguous(registration: Registration, peopleWithClubMemberId: List<Person>)
        fun onUnmappedClubMemberIdMatchButNameMismatch(registration: Registration, person: Person)
        fun onUnmappedExactMatch(registration: Registration, person: Person)
        fun onUnused(key: Event.CrispyFishMetadata.PeopleMapKey, person: Person)
    }

    /**
     * Callback which throws on every method except onMapped
     */
    class ThrowingCallback : Callback {

        override fun onMapped(registration: Registration, entry: Pair<Event.CrispyFishMetadata.PeopleMapKey, Person>) {
            // no-op
        }

        override fun onUnmappedMotorsportRegPersonExactMatch(
            registration: Registration,
            entry: Pair<Event.CrispyFishMetadata.PeopleMapKey, Person>
        ) {
            throw VerificationException()
        }

        override fun onUnmappedExactMatch(registration: Registration, person: Person) {
            throw VerificationException()
        }

        override fun onUnmappedClubMemberIdNull(registration: Registration) {
            throw VerificationException()
        }

        override fun onUnmappedClubMemberIdNotFound(registration: Registration) {
            throw VerificationException()
        }

        override fun onUnmappedClubMemberIdAmbiguous(registration: Registration, peopleWithClubMemberId: List<Person>) {
            throw VerificationException()
        }

        override fun onUnmappedClubMemberIdMatchButNameMismatch(registration: Registration, person: Person) {
            throw VerificationException()
        }

        override fun onUnused(key: Event.CrispyFishMetadata.PeopleMapKey, person: Person) {
            throw VerificationException()
        }
    }
}