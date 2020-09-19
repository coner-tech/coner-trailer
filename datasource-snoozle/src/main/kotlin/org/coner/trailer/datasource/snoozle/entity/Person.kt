package org.coner.trailer.datasource.snoozle.entity

import org.coner.snoozle.db.entity.Entity
import java.util.*

data class PersonEntity(
        val id: UUID = UUID.randomUUID(),
        val memberId: String?,
        val firstName: String,
        val lastName: String
) : Entity<PersonEntity.Key> {

    data class Key(val id: UUID) : org.coner.snoozle.db.Key
}