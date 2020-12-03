package org.coner.trailer.io.mapper

import org.coner.trailer.Event
import org.coner.trailer.Grouping
import org.coner.trailer.Participant
import org.coner.trailer.datasource.snoozle.entity.EventEntity
import org.coner.trailer.datasource.snoozle.entity.GroupingContainer
import org.coner.trailer.datasource.snoozle.entity.ParticipantEntity
import org.coner.trailer.io.service.CrispyFishGroupingService
import org.coner.trailer.io.service.PersonService

class EventMapper(
    private val personService: PersonService,
    private val crispyFishGroupingService: CrispyFishGroupingService,
) {

    fun toCore(snoozle: EventEntity): Event {
        return Event(
            id = snoozle.id,
            name = snoozle.name,
            date = snoozle.date,
            crispyFish = Event.CrispyFishMetadata(
                eventControlFile = snoozle.crispyFish.eventControlFile,
                forceParticipantSignageToPerson = snoozle.crispyFish.forceParticipantSignageToPersonId.map { (entitySignage, personId) ->
                    val grouping = when (entitySignage.grouping.type) {
                        GroupingContainer.Type.SINGULAR -> crispyFishGroupingService.findSingular()
                        GroupingContainer.Type.PAIR -> crispyFishGroupingService.findPaired()
                    }
                    val person = personService.findById(personId)
                    val signage = Participant.Signage(
                        grouping = grouping,
                        number = entitySignage.number
                    )
                    signage to person
                }.toMap()
            )
        )
    }

    fun toSnoozle(core: Event): EventEntity {
        return EventEntity(
            id = core.id,
            name = core.name,
            date = core.date,
            crispyFish = EventEntity.CrispyFishMetadata(
                eventControlFile = core.crispyFish.eventControlFile,
                classDefinitionFile = core.crispyFish.classDefinitionFile,
                forceParticipantSignageToPersonId = core.crispyFish.forceParticipantSignageToPerson.map { (coreSignage, corePerson) ->
                    val grouping = crispyFishGroupingService.
                    val signage = ParticipantEntity.Signage(
                        grouping = grouping,
                        number = coreSignage.number
                    )
                    signage to corePerson.id
                }.toMap()
            )
        )
    }

}
