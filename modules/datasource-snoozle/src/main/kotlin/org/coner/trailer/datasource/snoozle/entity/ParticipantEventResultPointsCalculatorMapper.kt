package org.coner.trailer.datasource.snoozle.entity

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

    fun fromCore(core: ParticipantEventResultPointsCalculator) = ParticipantEventResultPointsCalculatorEntity(
            id = core.id,
            name = core.name,
            positionToPoints = core.positionToPoints,
            didNotFinishPoints = core.didNotFinishPoints,
            didNotStartPoints = core.didNotStartPoints,
            defaultPoints = core.defaultPoints
    )
}