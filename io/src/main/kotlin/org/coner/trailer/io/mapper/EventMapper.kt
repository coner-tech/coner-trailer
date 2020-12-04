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
        val allSingularGroupings by lazy { crispyFishGroupingService.loadAllSingulars(
            crispyFishClassDefinitionFile = requireNotNull(snoozle.crispyFish).classDefinitionFile
        ) }
        return Event(
            id = snoozle.id,
            name = snoozle.name,
            date = snoozle.date,
            crispyFish = snoozle.crispyFish?.let { Event.CrispyFishMetadata(
                eventControlFile = it.eventControlFile,
                classDefinitionFile = it.classDefinitionFile,
                forceParticipantSignageToPerson = it.forceParticipantSignageToPersonId.map { (entitySignage, personId) ->
                    val grouping = when (entitySignage.grouping.type) {
                        GroupingContainer.Type.SINGULAR -> crispyFishGroupingService.findSingular(
                            allSingulars = allSingularGroupings,
                            abbreviation = requireNotNull(entitySignage.grouping.singular)
                        )
                        GroupingContainer.Type.PAIR -> crispyFishGroupingService.findPaired(
                            allSingulars = allSingularGroupings,
                            abbreviations = requireNotNull(entitySignage.grouping.pair)
                        )
                    }
                    val person = personService.findById(personId)
                    val signage = Participant.Signage(
                        grouping = grouping,
                        number = entitySignage.number
                    )
                    signage to person
                }.toMap()
            ) }
        )
    }

    fun toSnoozle(core: Event): EventEntity {
        return EventEntity(
            id = core.id,
            name = core.name,
            date = core.date,
            crispyFish = core.crispyFish?.let { EventEntity.CrispyFishMetadata(
                eventControlFile = it.eventControlFile,
                classDefinitionFile = it.classDefinitionFile,
                forceParticipantSignageToPersonId = it.forceParticipantSignageToPerson.map { (coreSignage, corePerson) ->
                    val grouping = when (val grouping = coreSignage.grouping) {
                        is Grouping.Singular -> GroupingContainer(
                            type = GroupingContainer.Type.SINGULAR,
                            singular = grouping.abbreviation
                        )
                        is Grouping.Paired -> GroupingContainer(
                            type = GroupingContainer.Type.PAIR,
                            pair = grouping.pair.first.abbreviation to grouping.pair.second.abbreviation
                        )
                    }
                    val signage = ParticipantEntity.Signage(
                        grouping = grouping,
                        number = coreSignage.number
                    )
                    signage to corePerson.id
                }.toMap()
            ) }
        )
    }

}
