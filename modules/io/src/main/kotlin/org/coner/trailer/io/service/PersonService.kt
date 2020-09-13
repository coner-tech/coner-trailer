package org.coner.trailer.io.service

import org.coner.trailer.Person
import org.coner.trailer.datasource.snoozle.PersonResource
import org.coner.trailer.datasource.snoozle.entity.PersonEntity
import org.coner.trailer.io.constraint.PersonPersistConstraints
import org.coner.trailer.io.mapper.PersonMapper
import java.util.*

class PersonService(
        private val persistConstraints: PersonPersistConstraints,
        private val resource: PersonResource,
        private val mapper: PersonMapper
) {

    fun create(create: Person) {
        persistConstraints.assess(create)
        resource.create(mapper.toSnoozle(create))
    }

    fun findById(id: UUID): Person {
        val key = PersonEntity.Key(id = id)
        return mapper.fromSnoozle(resource.read(key))
    }

}