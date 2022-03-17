package tech.coner.trailer.datasource.snoozle.entity

import tech.coner.snoozle.db.entity.Entity
import java.util.*

data class SeasonEntity(
        val id: UUID = UUID.randomUUID(),
        val name: String,
        val seasonEventIds: List<UUID>,
        val seasonPointsCalculatorConfigurationId: UUID,
        val takeScoreCountForPoints: Int?
) : Entity<SeasonEntity.Key>  {

    data class Key(val id: UUID) : tech.coner.snoozle.db.Key
}