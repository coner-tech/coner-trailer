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
            lifecycle = Event.Lifecycle.valueOf(snoozle.lifecycle),
            crispyFish = snoozle.crispyFish?.let {
                val phaseOne = Event.CrispyFishMetadata(
                    eventControlFile = it.eventControlFile,
                    classDefinitionFile = it.classDefinitionFile,
                    forcePeople = emptyMap()
                )
                val allSingularGroupings by lazy {
                    crispyFishGroupingService.loadAllSingulars(
                        crispyFish = phaseOne
                    )
                }
                phaseOne.copy(
                    forcePeople = it.forcePeople.map { force ->
                        val grouping = when (force.signage.grouping.type) {
                            GroupingContainer.Type.SINGULAR -> crispyFishGroupingService.findSingular(
                                allSingulars = allSingularGroupings,
                                abbreviation = requireNotNull(force.signage.grouping.singular)
                            )
                            GroupingContainer.Type.PAIR -> crispyFishGroupingService.findPaired(
                                allSingulars = allSingularGroupings,
                                abbreviations = requireNotNull(force.signage.grouping.pair)
                            )
                        }
                        val person = personService.findById(force.personId)
                        val signage = Participant.Signage(
                            grouping = grouping,
                            number = force.signage.number
                        )
                        signage to person
                    }.toMap()
                )
            }
        )
    }

    fun toSnoozle(core: Event): EventEntity {
        return EventEntity(
            id = core.id,
            name = core.name,
            date = core.date,
            lifecycle = core.lifecycle.toString(),
            crispyFish = core.crispyFish?.let { EventEntity.CrispyFishMetadata(
                eventControlFile = it.eventControlFile,
                classDefinitionFile = it.classDefinitionFile,
                forcePeople = it.forcePeople.map { (coreSignage, corePerson) ->
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
                    EventEntity.ForcePerson(
                        signage = ParticipantEntity.Signage(
                            grouping = grouping,
                            number = coreSignage.number
                        ),
                        personId = corePerson.id
                    )
                }
            ) }
        )
    }

}
