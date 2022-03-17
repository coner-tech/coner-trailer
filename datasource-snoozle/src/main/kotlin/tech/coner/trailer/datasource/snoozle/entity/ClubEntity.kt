package tech.coner.trailer.datasource.snoozle.entity

import tech.coner.snoozle.db.entity.Entity

data class ClubEntity(
    val name: String
) : Entity<ClubEntity.Key> {

    object Key : tech.coner.snoozle.db.Key
}
