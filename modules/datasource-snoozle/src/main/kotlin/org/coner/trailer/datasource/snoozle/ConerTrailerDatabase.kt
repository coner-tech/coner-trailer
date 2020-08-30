package org.coner.trailer.datasource.snoozle

import org.coner.snoozle.db.Database
import org.coner.snoozle.db.entity.EntityResource
import org.coner.trailer.datasource.snoozle.entity.ParticipantEventResultPointsCalculatorEntity
import java.nio.file.Path

class ConerTrailerDatabase(root: Path) : Database(root) {

    override val types = registerTypes {
        entity<ParticipantEventResultPointsCalculatorEntity.Key, ParticipantEventResultPointsCalculatorEntity> {
            path = "participantEventResultPointsCalculators" / { id } + ".json"
            keyFromPath = { ParticipantEventResultPointsCalculatorEntity.Key(id = uuidAt(0)) }
            keyFromEntity = { ParticipantEventResultPointsCalculatorEntity.Key(id = id) }
        }
    }
}

typealias ParticipantEventResultPointsCalculatorResource = EntityResource<ParticipantEventResultPointsCalculatorEntity.Key, ParticipantEventResultPointsCalculatorEntity>