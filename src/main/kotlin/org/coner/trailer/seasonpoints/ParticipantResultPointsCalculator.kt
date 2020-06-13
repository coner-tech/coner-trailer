package org.coner.trailer.seasonpoints

import org.coner.trailer.eventresults.ParticipantResult

class ParticipantResultPointsCalculator(
        private val didNotFinishPoints: Int? = null,
        private val didNotStartPoints: Int? = null,
        private val positionToPoints: Map<Int, Int>,
        private val defaultPoints: Int
) {
    fun calculate(participantResult: ParticipantResult): Int {
        return when {
            didNotFinishPoints != null && participantResult.personalBestRun?.didNotFinish ?: false -> didNotFinishPoints
            didNotStartPoints != null && participantResult.personalBestRun == null -> didNotStartPoints
            else -> positionToPoints[participantResult.position] ?: defaultPoints
        }
    }
}