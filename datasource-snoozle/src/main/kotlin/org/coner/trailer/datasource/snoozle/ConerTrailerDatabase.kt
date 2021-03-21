package org.coner.trailer.datasource.snoozle

import org.coner.snoozle.db.Database
import org.coner.snoozle.db.entity.EntityResource
import org.coner.trailer.datasource.snoozle.entity.*
import java.nio.file.Path

class ConerTrailerDatabase(root: Path) : Database(root) {

    override val types = registerTypes {
        entity<EventPointsCalculatorEntity.Key, EventPointsCalculatorEntity> {
            path = "eventPointsCalculators" / { id } + ".json"
            keyFromPath = { EventPointsCalculatorEntity.Key(id = uuidAt(0)) }
            keyFromEntity = { EventPointsCalculatorEntity.Key(id = id) }
        }
        entity<RankingSortEntity.Key, RankingSortEntity> {
            path = "rankingSorts" / { id } + ".json"
            keyFromPath = { RankingSortEntity.Key(id = uuidAt(0)) }
            keyFromEntity = { RankingSortEntity.Key(id = id) }
        }
        entity<SeasonPointsCalculatorConfigurationEntity.Key, SeasonPointsCalculatorConfigurationEntity> {
            path = "seasonPointsCalculatorConfigurations" / { id } + ".json"
            keyFromPath = { SeasonPointsCalculatorConfigurationEntity.Key(id = uuidAt(0)) }
            keyFromEntity = { SeasonPointsCalculatorConfigurationEntity.Key(id = id) }
        }
        entity<PersonEntity.Key, PersonEntity> {
            path = "people" / { id } + ".json"
            keyFromPath = { PersonEntity.Key(id = uuidAt(0)) }
            keyFromEntity = { PersonEntity.Key(id = id) }
        }
        entity<SeasonEntity.Key, SeasonEntity> {
            path = "seasons" / { id } + ".json"
            keyFromPath = { SeasonEntity.Key(id = uuidAt(0)) }
            keyFromEntity = { SeasonEntity.Key(id = id) }
        }
        entity<EventEntity.Key, EventEntity> {
            path = "events" / { id } + ".json"
            keyFromPath = { EventEntity.Key(id = uuidAt(0)) }
            keyFromEntity = { EventEntity.Key(id = id) }
        }
        entity<PolicyEntity.Key, PolicyEntity> {
            path = "policies" / { id } + ".json"
            keyFromPath = { PolicyEntity.Key(id = uuidAt(0)) }
            keyFromEntity = { PolicyEntity.Key(id = id) }
        }
    }
}

typealias EventPointsCalculatorResource = EntityResource<EventPointsCalculatorEntity.Key, EventPointsCalculatorEntity>
typealias RankingSortResource = EntityResource<RankingSortEntity.Key, RankingSortEntity>
typealias SeasonPointsCalculatorConfigurationResource = EntityResource<SeasonPointsCalculatorConfigurationEntity.Key, SeasonPointsCalculatorConfigurationEntity>
typealias PersonResource = EntityResource<PersonEntity.Key, PersonEntity>
typealias SeasonResource = EntityResource<SeasonEntity.Key, SeasonEntity>
typealias EventResource = EntityResource<EventEntity.Key, EventEntity>
typealias PolicyResource = EntityResource<PolicyEntity.Key, PolicyEntity>