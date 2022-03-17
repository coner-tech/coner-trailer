package tech.coner.trailer.io.mapper

import tech.coner.trailer.Classing
import tech.coner.trailer.Event
import tech.coner.trailer.datasource.snoozle.PolicyResource
import tech.coner.trailer.datasource.snoozle.entity.EventEntity
import tech.coner.trailer.datasource.snoozle.entity.ParticipantEntity
import tech.coner.trailer.datasource.snoozle.entity.PolicyEntity
import tech.coner.trailer.io.DatabaseConfiguration
import tech.coner.trailer.io.service.CrispyFishClassService
import tech.coner.trailer.io.service.PersonService
import java.nio.file.Paths

class EventMapper(
    private val dbConfig: DatabaseConfiguration,
    private val personService: PersonService,
    private val crispyFishClassService: CrispyFishClassService,
    private val policyResource: PolicyResource,
    private val policyMapper: PolicyMapper,
) {

    fun toCore(snoozle: EventEntity): Event {
        return Event(
            id = snoozle.id,
            name = snoozle.name,
            date = snoozle.date,
            lifecycle = Event.Lifecycle.valueOf(snoozle.lifecycle),
            crispyFish = snoozle.crispyFish?.let { crispyFishMetadata ->
                val crispyFishClassDefinitionFile = Paths.get(crispyFishMetadata.classDefinitionFile)
                val allClassesByAbbreviation = crispyFishClassService.loadAllClasses(crispyFishClassDefinitionFile)
                    .associateBy { it.abbreviation }
                Event.CrispyFishMetadata(
                    eventControlFile = Paths.get(crispyFishMetadata.eventControlFile),
                    classDefinitionFile = crispyFishClassDefinitionFile,
                    peopleMap = crispyFishMetadata.peopleMap.associate { force ->
                        val classing = Classing(
                            group = allClassesByAbbreviation[force.classing.group],
                            handicap = requireNotNull(allClassesByAbbreviation[force.classing.handicap])
                        )
                        val person = personService.findById(force.personId)
                        val key = Event.CrispyFishMetadata.PeopleMapKey(
                            classing = classing,
                            number = force.number,
                            firstName = force.firstName,
                            lastName = force.lastName,
                        )
                        key to person
                    }
                )
            },
            motorsportReg = snoozle.motorsportReg?.let { Event.MotorsportRegMetadata(id = it.id) },
            policy = policyMapper.toCore(policyResource.read(PolicyEntity.Key(snoozle.policyId))),
        )
    }

    fun toSnoozle(core: Event): EventEntity {
        return EventEntity(
            id = core.id,
            name = core.name,
            date = core.date,
            lifecycle = core.lifecycle.toString(),
            crispyFish = core.crispyFish?.let { EventEntity.CrispyFishMetadata(
                eventControlFile = it.eventControlFile.toString(),
                classDefinitionFile = it.classDefinitionFile.toString(),
                peopleMap = it.peopleMap.map { (key, value) ->
                    EventEntity.PersonMapEntry(
                        classing = ParticipantEntity.Classing(
                            group = key.classing.group?.abbreviation,
                            handicap = key.classing.handicap.abbreviation
                        ),
                        number = key.number,
                        firstName = key.firstName,
                        lastName = key.lastName,
                        personId = value.id
                    )
                }
            ) },
            motorsportReg = core.motorsportReg?.let { EventEntity.MotorsportRegMetadata(id = it.id) },
            policyId = core.policy.id,
        )
    }
}
