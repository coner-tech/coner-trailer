package org.coner.trailer.io.verification

import org.coner.crispyfish.model.Registration
import org.coner.trailer.Event
import org.coner.trailer.Person
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.io.service.PersonService

class EventCrispyFishPersonMapVerifier(
    private val personService: PersonService,
    private val crispyFishParticipantMapper: CrispyFishParticipantMapper
) {

    fun verify(
        context: CrispyFishEventMappingContext,
        peopleMap: Map<Event.CrispyFishMetadata.PeopleMapKey, Person>
    ) : List<Outcome> {
        val clubMemberIdToPeople: Map<String?, List<Person>> = personService.list().groupBy { it.clubMemberId }
        return context.allRegistrations.map { registration ->
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
                Outcome.Mapped(registration, mappedPerson)
            } else {
                val memberNumber = registration.memberNumber
                if (memberNumber.isNullOrEmpty()) {
                    Outcome.NotMapped.WithoutClubMemberId(registration)
                }
                val peopleWithMemberId = clubMemberIdToPeople[registration.memberNumber]
                when {
                    peopleWithMemberId == null -> Outcome.NotMapped.WithClubMemberIdNotFound(registration)
                    peopleWithMemberId.size > 1 -> Outcome.NotMapped.MultiplePeopleWithClubMemberId(registration, peopleWithMemberId)
                    peopleWithMemberId.size == 1 -> {
                        val personWithMemberId = peopleWithMemberId.single()
                        if (registration.firstName == personWithMemberId.firstName
                            && registration.lastName == personWithMemberId.lastName) {
                            Outcome.NotMapped.WithExactMatch(registration, personWithMemberId)
                        } else {
                            Outcome.NotMapped.WithClubMemberIdMatchButNameMismatch(registration, personWithMemberId)
                        }
                    }
                    else -> throw IllegalStateException()
                }
            }
        }
    }

    sealed class Outcome {
        data class Mapped(val registration: Registration, val person: Person) : Outcome()
        sealed class NotMapped : Outcome() {
            data class WithExactMatch(val registration: Registration, val person: Person) : NotMapped()
            data class WithoutClubMemberId(val registration: Registration) : NotMapped()
            data class WithClubMemberIdNotFound(val registration: Registration) : NotMapped()
            data class MultiplePeopleWithClubMemberId(val registration: Registration, val peopleWithClubMemberId: List<Person>) : NotMapped()
            data class WithClubMemberIdMatchButNameMismatch(val registration: Registration, val person: Person) : NotMapped()
        }
    }
}