package org.coner.trailer.seasonpoints

import org.coner.trailer.eventresults.ParticipantResult
import java.util.*

class ParticipantEventResultPointsCalculator(
        val id: UUID = UUID.randomUUID(),
        val name: String,
        val didNotFinishPoints: Int? = null,
        val didNotStartPoints: Int? = null,
        val positionToPoints: Map<Int, Int>,
        val defaultPoints: Int
) {
    fun calculate(participantResult: ParticipantResult): Int {
        return when {
            didNotFinishPoints != null && participantResult.personalBestRun?.didNotFinish ?: false -> didNotFinishPoints
            didNotStartPoints != null && participantResult.personalBestRun == null -> didNotStartPoints
            else -> positionToPoints[participantResult.position] ?: defaultPoints
        }
    }
}