package org.coner.trailer.io.mapper

import org.coner.trailer.datasource.snoozle.entity.ParticipantEventResultPointsCalculatorEntity
import org.coner.trailer.seasonpoints.ParticipantEventResultPointsCalculator

class ParticipantEventResultPointsCalculatorMapper {

    fun fromSnoozle(snoozle: ParticipantEventResultPointsCalculatorEntity) = ParticipantEventResultPointsCalculator(
            id = snoozle.id,
            name = snoozle.name,
            positionToPoints = snoozle.positionToPoints,
            didNotFinishPoints = snoozle.didNotFinishPoints,
            didNotStartPoints = snoozle.didNotStartPoints,
            defaultPoints = snoozle.defaultPoints
    )

    fun toSnoozle(core: ParticipantEventResultPointsCalculator) = ParticipantEventResultPointsCalculatorEntity(
            id = core.id,
            name = core.name,
            positionToPoints = core.positionToPoints,
            didNotFinishPoints = core.didNotFinishPoints,
            didNotStartPoints = core.didNotStartPoints,
            defaultPoints = core.defaultPoints
    )
}