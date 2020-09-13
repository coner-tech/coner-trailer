package org.coner.trailer.io.service

import org.coner.trailer.Person
import org.coner.trailer.datasource.snoozle.PersonResource
import org.coner.trailer.io.mapper.PersonMapper

class PersonService(
        private val resource: PersonResource,
        private val mapper: PersonMapper
) {

    fun create(create: Person) {
        TODO()
    }
}