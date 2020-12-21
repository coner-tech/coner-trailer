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
        failureCallback: FailureCallback?
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
            val memberNumber = registration.memberNumber
            if (memberNumber.isNullOrEmpty()) {
                failureCallback?.onRegistrationWithoutMemberNumber(registration)
                success = false
                continue
            }
            val peopleWithMemberId = clubMemberIdToPeople[registration.memberNumber]
            if (peopleWithMemberId == null) {
                failureCallback?.onPersonWithClubMemberIdNotFound(registration)
                success = false
                continue
            }
            if (peopleWithMemberId.size > 1) {
                failureCallback?.onMultiplePeopleWithClubMemberIdFound(registration)
                success = false
                continue
            }
        }
        if (!success) {
            throw VerificationException()
        }
    }


    interface FailureCallback {
        fun onRegistrationWithoutMemberNumber(registration: Registration)
        fun onPersonWithClubMemberIdNotFound(registration: Registration)
        fun onMultiplePeopleWithClubMemberIdFound(registration: Registration)
    }
}