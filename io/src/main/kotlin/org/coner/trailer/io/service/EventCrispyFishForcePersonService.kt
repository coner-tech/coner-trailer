package org.coner.trailer.io.service

import org.coner.trailer.Person
import org.coner.trailer.datasource.crispyfish.CrispyFishEventMappingContext

class EventCrispyFishForcePersonService(
    private val personService: PersonService
) {

    fun verify(
        context: CrispyFishEventMappingContext
    ) {
        val clubMemberIdToPeople: Map<String?, List<Person>> = personService.list()
            .groupBy { it.clubMemberId }
        for (registration in context.allRegistrations) {
            val peopleWithMemberId = clubMemberIdToPeople[registration.memberNumber]
            TODO("handle single person with member ID, or multiple people, or none")
        }
    }
}