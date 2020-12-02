package org.coner.trailer.io.mapper

import org.coner.trailer.Event
import org.coner.trailer.datasource.snoozle.entity.EventEntity
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
                            val grouping = crispyFishGroupingService.findBySnoozleGroupingContainer(entitySignage.grouping)
                             crispyFishGroupingService. personService.findById(personId)
                        }
                )
        )
    }

    fun toSnoozle(core: Event): EventEntity {
        return EventEntity(
                id = core.id,
                name = core.name,
                date = core.date,
                crispyFish = EventEntity.CrispyFishMetadata(
                        eventControlFile = core.crispyFish?.eventControlFile
                )
        )
    }

}
