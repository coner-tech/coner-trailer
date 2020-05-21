package org.coner.trailer.seasonpoints

import org.coner.trailer.eventresults.ParticipantResult

class ParticipantResultPositionMappedPointsCalculator(
        private val positionToPointsMap: Map<Int, Int>,
        private val defaultPoints: Int
) {
    fun calculate(participantResult: ParticipantResult): Int {
        return positionToPointsMap[participantResult.position] ?: defaultPoints
    }
}