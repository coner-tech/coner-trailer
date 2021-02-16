package org.coner.trailer.io.verification

import org.coner.crispyfish.model.Registration
import org.coner.trailer.Event
import org.coner.trailer.Person
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishGroupingMapper
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.io.mapper.MotorsportRegParticipantMapper
import org.coner.trailer.io.service.CrispyFishGroupingService
import org.coner.trailer.io.service.MotorsportRegEventService
import org.coner.trailer.io.service.MotorsportRegPeopleMapService
import org.coner.trailer.io.service.PersonService

class EventCrispyFishPersonMapVerifier(
    private val personService: PersonService,
    private val motorsportRegPeopleMapService: MotorsportRegPeopleMapService,
    private val motorsportRegEventService: MotorsportRegEventService,
    private val motorsportRegParticipantMapper: MotorsportRegParticipantMapper,
    private val crispyFishParticipantMapper: CrispyFishParticipantMapper,
    private val crispyFishGroupingService: CrispyFishGroupingService
) {

    fun verify(
        event: Event,
        context: CrispyFishEventMappingContext,
        peopleMap: Map<Event.CrispyFishMetadata.PeopleMapKey, Person>,
        callback: Callback
    ) {
        val clubMemberIdToPeople: Map<String?, List<Person>> = personService.list().groupBy { it.clubMemberId }
        val msrAttendees = event.motorsportReg?.id?.let { motorsportRegEventService.fetchAssignments(it) } ?: emptyList()
        val groupingsByAbbreviation = event.crispyFish?.let { crispyFishGroupingService.loadAllSingularGroupingsByAbbreviation(it) } ?: emptyMap()
        val msrParticipants = msrAttendees.map { motorsportRegParticipantMapper.toCore(
            groupingsByAbbreviation = groupingsByAbbreviation,
            motorsportRegAssignment = it
        ) }
        TODO("extract to MotorsportRegPeopleMapService")
        val msrPeopleMap = msrParticipants.map {
            val peopleMapKey = Event.CrispyFishMetadata.PeopleMapKey(
                signage = it.signage,
                firstName = it.firstName,
                lastName = it.lastName
            )
            peopleMapKey to it.person
        }.toMap()
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
            val mappedPerson = peopleMap[key]
            if (mappedPerson != null) {
                usedKeys += key
                callback.onMapped(registration, mappedPerson)
            } else {
                val memberNumber = registration.memberNumber
                if (memberNumber.isNullOrEmpty()) {
                    callback.onUnmappedClubMemberIdNull(registration)
                    continue
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
        }
        val unusedMappings = peopleMap.filter { !usedKeys.contains(it.key) }
        for (unusedMapping in unusedMappings) {
            callback.onUnused(unusedMapping.key, unusedMapping.value)
        }
    }

    interface Callback {
        fun onMapped(registration: Registration, person: Person)
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
        override fun onMapped(registration: Registration, person: Person) {
            // no-op
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