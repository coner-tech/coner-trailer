package org.coner.trailer.datasource.snoozle.entity

import org.coner.snoozle.db.entity.Entity
import java.util.*

data class PolicyEntity(
    val id: UUID,
    val name: String,
    val conePenaltySeconds: Int,
    val finalScoreStyle: String
) : Entity<PolicyEntity.Key> {

    data class Key(val id: UUID) : org.coner.snoozle.db.Key
}