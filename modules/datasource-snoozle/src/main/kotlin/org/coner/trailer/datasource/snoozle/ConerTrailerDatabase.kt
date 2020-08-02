package org.coner.trailer.datasource.snoozle

import org.coner.snoozle.db.Database
import org.coner.trailer.datasource.snoozle.entity.ParticipantEventResultPointsCalculatorEntity
import java.nio.file.Path

class ConerTrailerDatabase(root: Path) : Database(root) {

    override val types = registerTypes {
        entity<ParticipantEventResultPointsCalculatorEntity> {
            path = "participantEventResultPointsCalculators" / { it.id }
        }
    }
}