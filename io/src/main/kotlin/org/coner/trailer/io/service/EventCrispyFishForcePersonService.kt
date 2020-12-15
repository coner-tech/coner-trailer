package org.coner.trailer.io.service

import org.coner.trailer.Person
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext

class EventCrispyFishForcePersonService(
    private val personService: PersonService
) {

    fun verifyRegistrations(
        context: CrispyFishEventMappingContext,
        failureCallback: EventCrispyFishForcePersonVerificationFailureCallback?
    ) {
        val clubMemberIdToPeople: Map<String?, List<Person>> = personService.list()
            .groupBy { it.clubMemberId }
        for (registration in context.allRegistrations) {
            val memberNumber = registration.memberNumber
            if (memberNumber.isNullOrEmpty()) {
                failureCallback?.onRegistrationWithoutMemberNumber(registration)
                continue
            }
            val peopleWithMemberId = clubMemberIdToPeople[registration.memberNumber]
            if (peopleWithMemberId == null) {
                failureCallback?.onPersonWithClubMemberIdNotFound(registration)
                continue
            }
            if (peopleWithMemberId.size > 1) {
                failureCallback?.onMultiplePeopleWithClubMemberIdFound(registration)
                continue
            }
        }
    }
}