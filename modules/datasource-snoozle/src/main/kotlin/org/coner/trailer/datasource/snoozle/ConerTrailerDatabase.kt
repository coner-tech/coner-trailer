package org.coner.trailer.datasource.snoozle

import org.coner.snoozle.db.Database
import org.coner.snoozle.db.entity.EntityResource
import org.coner.trailer.datasource.snoozle.entity.ParticipantEventResultPointsCalculatorEntity
import org.coner.trailer.datasource.snoozle.entity.RankingSortEntity
import org.coner.trailer.datasource.snoozle.entity.SeasonPointsCalculatorConfigurationEntity
import java.nio.file.Path

class ConerTrailerDatabase(root: Path) : Database(root) {

    override val types = registerTypes {
        entity<ParticipantEventResultPointsCalculatorEntity.Key, ParticipantEventResultPointsCalculatorEntity> {
            path = "participantEventResultPointsCalculators" / { id } + ".json"
            keyFromPath = { ParticipantEventResultPointsCalculatorEntity.Key(id = uuidAt(0)) }
            keyFromEntity = { ParticipantEventResultPointsCalculatorEntity.Key(id = id) }
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
    }
}

typealias ParticipantEventResultPointsCalculatorResource = EntityResource<ParticipantEventResultPointsCalculatorEntity.Key, ParticipantEventResultPointsCalculatorEntity>
typealias RankingSortResource = EntityResource<RankingSortEntity.Key, RankingSortEntity>
typealias SeasonPointsCalculatorConfigurationResource = EntityResource<SeasonPointsCalculatorConfigurationEntity.Key, SeasonPointsCalculatorConfigurationEntity>