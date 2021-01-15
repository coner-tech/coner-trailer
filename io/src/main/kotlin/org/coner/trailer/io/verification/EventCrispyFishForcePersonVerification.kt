package org.coner.trailer.io.verification

import org.coner.crispyfish.model.Registration
import org.coner.trailer.Participant
import org.coner.trailer.Person
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext
import org.coner.trailer.datasource.crispyfish.CrispyFishParticipantMapper
import org.coner.trailer.io.service.PersonService

class EventCrispyFishForcePersonVerification(
    private val personService: PersonService,
    private val crispyFishParticipantMapper: CrispyFishParticipantMapper
) {

    fun verifyRegistrations(
        context: CrispyFishEventMappingContext,
        forcePeople: Map<Participant.Signage, Person>,
        callback: Callback?
    ) {
        var success = true
        val clubMemberIdToPeople: Map<String?, List<Person>> = personService.list()
            .groupBy { it.clubMemberId }
        for (registration in context.allRegistrations) {
            val signage = crispyFishParticipantMapper.toCoreSignage(
                context = context,
                crispyFish = registration
            )
            if (forcePeople[signage] != null) {
                continue
            }
            success = false
            val memberNumber = registration.memberNumber
            if (memberNumber.isNullOrEmpty()) {
                callback?.onRegistrationWithoutClubMemberId(registration)
                continue
            }
            val peopleWithMemberId = clubMemberIdToPeople[registration.memberNumber]
            when {
                peopleWithMemberId == null -> callback?.onPersonWithClubMemberIdNotFound(registration)
                peopleWithMemberId.size > 1 -> callback?.onMultiplePeopleWithClubMemberIdFound(registration)
                peopleWithMemberId.size == 1 -> {
                    val personWithMemberId = peopleWithMemberId.single()
                    if (registration.firstName == personWithMemberId.firstName
                        && registration.lastName == personWithMemberId.lastName) {
                        callback?.onUnforcedExactMatchFound(registration, personWithMemberId)
                    } else {
                        callback?.onRegistrationWithClubMemberIdFound(registration, personWithMemberId)
                    }
                }
            }
        }
        if (!success) {
            throw VerificationException()
        }
    }


    interface Callback {
        fun onUnforcedExactMatchFound(registration: Registration, person: Person)
        fun onRegistrationWithClubMemberIdFound(registration: Registration, person: Person)
        fun onRegistrationWithoutClubMemberId(registration: Registration)
        fun onPersonWithClubMemberIdNotFound(registration: Registration)
        fun onMultiplePeopleWithClubMemberIdFound(registration: Registration)
    }
}