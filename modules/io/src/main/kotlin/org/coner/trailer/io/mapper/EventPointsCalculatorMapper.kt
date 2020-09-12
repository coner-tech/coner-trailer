package org.coner.trailer.io.mapper

import org.coner.trailer.datasource.snoozle.entity.EventPointsCalculatorEntity
import org.coner.trailer.seasonpoints.EventPointsCalculator

class EventPointsCalculatorMapper {

    fun fromSnoozle(snoozle: EventPointsCalculatorEntity) = EventPointsCalculator(
            id = snoozle.id,
            name = snoozle.name,
            positionToPoints = snoozle.positionToPoints,
            didNotFinishPoints = snoozle.didNotFinishPoints,
            didNotStartPoints = snoozle.didNotStartPoints,
            defaultPoints = snoozle.defaultPoints
    )

    fun toSnoozle(core: EventPointsCalculator) = EventPointsCalculatorEntity(
            id = core.id,
            name = core.name,
            positionToPoints = core.positionToPoints,
            didNotFinishPoints = core.didNotFinishPoints,
            didNotStartPoints = core.didNotStartPoints,
            defaultPoints = core.defaultPoints
    )
}