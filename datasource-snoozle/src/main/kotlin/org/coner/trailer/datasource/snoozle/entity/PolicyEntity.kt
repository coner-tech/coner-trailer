package org.coner.trailer.datasource.snoozle.entity

import org.coner.snoozle.db.entity.Entity
import org.coner.trailer.eventresults.PaxTimeStyle
import java.util.*

data class PolicyEntity(
    val id: UUID,
    val name: String,
    val conePenaltySeconds: Int,
    val paxTimeStyle: String,
    val finalScoreStyle: String
) : Entity<PolicyEntity.Key> {

    data class Key(val id: UUID) : org.coner.snoozle.db.Key
}