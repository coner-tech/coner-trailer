package org.coner.trailer.io.mapper

import org.coner.trailer.Person
import org.coner.trailer.datasource.snoozle.entity.PersonEntity

class PersonMapper {

    fun fromSnoozle(snoozle: PersonEntity): Person = Person(
            id = snoozle.id,
            firstName = snoozle.firstName,
            lastName = snoozle.lastName,
            memberId = snoozle.memberId
    )

    fun toSnoozle(core: Person): PersonEntity = PersonEntity(
            id = core.id,
            firstName = core.firstName,
            lastName = core.lastName,
            memberId = core.memberId
    )
}