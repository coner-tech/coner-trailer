package tech.coner.trailer.datasource.snoozle.entity

import tech.coner.snoozle.db.entity.Entity
import java.util.*

data class PolicyEntity(
    val id: UUID,
    val name: String,
    val conePenaltySeconds: Int,
    val paxTimeStyle: String,
    val finalScoreStyle: String,
    val authoritativeParticipantSource: DataSource = DataSource(DataSource.Type.CRISPY_FISH),
    val authoritativeRunSource: DataSource = DataSource(DataSource.Type.CRISPY_FISH)
) : Entity<PolicyEntity.Key> {

    data class Key(val id: UUID) : tech.coner.snoozle.db.Key

    data class DataSource(val type: Type) {
        enum class Type {
            CRISPY_FISH
        }
    }
}