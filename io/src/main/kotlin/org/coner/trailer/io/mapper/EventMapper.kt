package org.coner.trailer.io.mapper

import org.coner.trailer.Event
import org.coner.trailer.Grouping
import org.coner.trailer.Participant
import org.coner.trailer.datasource.snoozle.PolicyResource
import org.coner.trailer.datasource.snoozle.entity.EventEntity
import org.coner.trailer.datasource.snoozle.entity.GroupingContainer
import org.coner.trailer.datasource.snoozle.entity.ParticipantEntity
import org.coner.trailer.datasource.snoozle.entity.PolicyEntity
import org.coner.trailer.io.service.CrispyFishGroupingService
import org.coner.trailer.io.service.PersonService
import org.coner.trailer.io.service.PolicyService

class EventMapper(
    private val personService: PersonService,
    private val crispyFishGroupingService: CrispyFishGroupingService,
    private val policyResource: PolicyResource,
    private val policyMapper: PolicyMapper,
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
                    peopleMap = emptyMap()
                )
                val allSingularGroupings by lazy {
                    crispyFishGroupingService.loadAllSingulars(
                        crispyFish = phaseOne
                    )
                }
                phaseOne.copy(
                    peopleMap = it.peopleMap.map { force ->
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
                        val key = Event.CrispyFishMetadata.PeopleMapKey(
                            grouping = grouping,
                            number = force.signage.number,
                            firstName = force.firstName,
                            lastName = force.lastName,
                        )
                        key to person
                    }.toMap()
                )
            },
            motorsportReg = snoozle.motorsportReg?.let { Event.MotorsportRegMetadata(id = it.id) },
            policy = policyMapper.toCore(policyResource.read(PolicyEntity.Key(snoozle.policyId)))
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
                peopleMap = it.peopleMap.map { (key, value) ->
                    val number = key.number
                    val grouping = when (val grouping = key.grouping) {
                        is Grouping.Singular -> GroupingContainer(
                            type = GroupingContainer.Type.SINGULAR,
                            singular = grouping.abbreviation
                        )
                        is Grouping.Paired -> GroupingContainer(
                            type = GroupingContainer.Type.PAIR,
                            pair = grouping.pair.first.abbreviation to grouping.pair.second.abbreviation
                        )
                    }
                    EventEntity.PersonMapEntry(
                        signage = ParticipantEntity.Signage(
                            grouping = grouping,
                            number = number
                        ),
                        firstName = key.firstName,
                        lastName = key.lastName,
                        personId = value.id
                    )
                }
            ) },
            motorsportReg = core.motorsportReg?.let { EventEntity.MotorsportRegMetadata(id = it.id) },
            policyId = core.policy.id
        )
    }

}
