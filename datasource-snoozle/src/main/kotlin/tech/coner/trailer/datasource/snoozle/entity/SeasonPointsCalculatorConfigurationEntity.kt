package tech.coner.trailer.datasource.snoozle.entity

import tech.coner.snoozle.db.entity.Entity
import java.util.*

data class SeasonPointsCalculatorConfigurationEntity(
        val id: UUID = UUID.randomUUID(),
        val name: String,
        val resultsTypeKeyToEventPointsCalculatorId: Map<String, UUID>,
        val rankingSortId: UUID
) : Entity<SeasonPointsCalculatorConfigurationEntity.Key> {

    data class Key(val id: UUID) : tech.coner.snoozle.db.Key

}
