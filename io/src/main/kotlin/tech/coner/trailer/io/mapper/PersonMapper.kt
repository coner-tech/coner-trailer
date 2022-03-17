package tech.coner.trailer.io.mapper

import tech.coner.trailer.Person
import tech.coner.trailer.datasource.snoozle.entity.PersonEntity

class PersonMapper {

    fun fromSnoozle(snoozle: PersonEntity): Person = Person(
            id = snoozle.id,
            firstName = snoozle.firstName,
            lastName = snoozle.lastName,
            clubMemberId = snoozle.clubMemberId,
            motorsportReg = snoozle.motorsportReg?.memberId?.let {
                Person.MotorsportRegMetadata(
                        memberId = it
                )
            }
    )

    fun toSnoozle(core: Person): PersonEntity = PersonEntity(
            id = core.id,
            firstName = core.firstName,
            lastName = core.lastName,
            clubMemberId = core.clubMemberId,
            motorsportReg = core.motorsportReg?.memberId?.let {
                PersonEntity.MotorsportRegMetadata(
                        memberId = it
                )
            }
    )
}