package org.coner.trailer.io.mapper

import org.coner.trailer.Event
import org.coner.trailer.datasource.snoozle.entity.EventEntity
import org.coner.trailer.io.service.PersonService

class EventMapper(
        private val personService: PersonService
) {

    fun toCore(snoozle: EventEntity): Event {
        return Event(
                id = snoozle.id,
                name = snoozle.name,
                date = snoozle.date,
                crispyFish = snoozle.crispyFish?.let {
                    Event.CrispyFishMetadata(
                            eventControlFile = it.eventControlFile,
                            forceParticipantSignageToPerson = it.forceParticipantSignageToPersonId.map { (entitySignage, personId) ->
                                 personService.findById(personId)
                            }
                    )
                }
        )
    }

}
