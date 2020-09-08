package org.coner.trailer.datasource.snoozle.entity

import org.coner.snoozle.db.entity.Entity
import java.util.*

data class SeasonPointsCalculatorConfigurationEntity(
        val id: UUID = UUID.randomUUID(),
        val name: String,
        val resultsTypeToCalculatorMap: Map<String, UUID>,
        val rankingSortId: UUID,
        val takeTopEventScores: Int?
) : Entity<SeasonPointsCalculatorConfigurationEntity.Key> {

    data class Key(val id: UUID) : org.coner.snoozle.db.Key

}
