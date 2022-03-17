package tech.coner.trailer.datasource.snoozle.entity

import tech.coner.snoozle.db.entity.Entity
import java.util.*

data class PersonEntity(
        val id: UUID = UUID.randomUUID(),
        val clubMemberId: String?,
        val firstName: String,
        val lastName: String,
        val motorsportReg: MotorsportRegMetadata? = null
) : Entity<PersonEntity.Key> {

    data class MotorsportRegMetadata(val memberId: String)

    data class Key(val id: UUID) : tech.coner.snoozle.db.Key
}