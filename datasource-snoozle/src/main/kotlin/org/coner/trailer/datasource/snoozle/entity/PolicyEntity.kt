package org.coner.trailer.datasource.snoozle.entity

import tech.coner.snoozle.db.entity.Entity
import org.coner.trailer.eventresults.PaxTimeStyle
import java.util.*

data class PolicyEntity(
    val id: UUID,
    val name: String,
    val conePenaltySeconds: Int,
    val paxTimeStyle: String,
    val finalScoreStyle: String,
    val authoritativeRunSource: RunSource = RunSource(RunSource.Type.CRISPY_FISH)
) : Entity<PolicyEntity.Key> {

    data class Key(val id: UUID) : tech.coner.snoozle.db.Key

    data class RunSource(val type: Type) {
        enum class Type {
            CRISPY_FISH
        }
    }
}