package org.coner.trailer.io.service

import org.coner.trailer.Event
import org.coner.trailer.Person
import org.coner.trailer.io.mapper.MotorsportRegParticipantMapper

class MotorsportRegPeopleMapService(
    private val motorsportRegEventService: MotorsportRegEventService,
    private val motorsportRegParticipantMapper: MotorsportRegParticipantMapper,
    private val crispyFishClassService: CrispyFishClassService
) {

    fun assemble(
        event: Event,
        peopleByMotorsportRegMemberId: Map<String, Person>
    ): Map<Event.CrispyFishMetadata.PeopleMapKey, Person> {
        val allClassesByAbbreviation = event.crispyFish?.classDefinitionFile?.let(crispyFishClassService::loadAllByAbbreviation) ?: emptyMap()
        val msrAttendees = event.motorsportReg?.id?.let { motorsportRegEventService.fetchAssignments(it) } ?: emptyList()
        val msrParticipants = msrAttendees
            .map { motorsportRegParticipantMapper.toCore(
                peopleByMotorsportRegMemberId = peopleByMotorsportRegMemberId,
                allClassesByAbbreviation = allClassesByAbbreviation,
                motorsportRegAssignment = it
            ) }
        return msrParticipants.mapNotNull { participant ->
            participant.person?.let { person ->
                val peopleMapKey = Event.CrispyFishMetadata.PeopleMapKey(
                    classing = participant.classing ?: return@let null,
                    number = participant.number ?: return@let null,
                    firstName = participant.firstName ?: return@let null,
                    lastName = participant.lastName ?: return@let null
                )
                peopleMapKey to person
            }
        }.toMap()
    }
}