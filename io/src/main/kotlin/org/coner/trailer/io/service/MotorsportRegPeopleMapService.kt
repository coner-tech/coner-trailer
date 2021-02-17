package org.coner.trailer.io.service

import org.coner.trailer.Event
import org.coner.trailer.Person
import org.coner.trailer.io.mapper.MotorsportRegParticipantMapper

class MotorsportRegPeopleMapService(
    private val motorsportRegEventService: MotorsportRegEventService,
    private val motorsportRegParticipantMapper: MotorsportRegParticipantMapper,
    private val crispyFishGroupingService: CrispyFishGroupingService
) {

    fun assemble(
        event: Event,
        peopleByMotorsportRegMemberId: Map<String, Person>
    ): Map<Event.CrispyFishMetadata.PeopleMapKey, Person> {
        val groupingsByAbbreviation = event.crispyFish?.let {
            crispyFishGroupingService.loadAllSingularGroupingsByAbbreviation(it)
        } ?: emptyMap()
        val msrAttendees = event.motorsportReg?.id?.let { motorsportRegEventService.fetchAssignments(it) } ?: emptyList()
        val msrParticipants = msrAttendees
            .map { motorsportRegParticipantMapper.toCore(
                peopleByMotorsportRegMemberId = peopleByMotorsportRegMemberId,
                groupingsByAbbreviation = groupingsByAbbreviation,
                motorsportRegAssignment = it
            ) }
        return msrParticipants.mapNotNull { participant ->
            participant.person?.let { person ->
                val peopleMapKey = Event.CrispyFishMetadata.PeopleMapKey(
                    signage = participant.signage,
                    firstName = participant.firstName,
                    lastName = participant.lastName
                )
                peopleMapKey to person
            }
        }.toMap()
    }
}