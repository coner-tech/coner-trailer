package tech.coner.trailer.seasonpoints

import tech.coner.trailer.eventresults.ParticipantResult
import java.util.*

data class EventPointsCalculator(
        val id: UUID = UUID.randomUUID(),
        val name: String,
        val didNotFinishPoints: Int? = null,
        val didNotStartPoints: Int? = null,
        val positionToPoints: Map<Int, Int>,
        val defaultPoints: Int
) {
    fun calculate(participantResult: ParticipantResult): Int {
        return when {
            didNotFinishPoints != null && participantResult.personalBestRun?.run?.didNotFinish ?: false -> didNotFinishPoints
            didNotStartPoints != null && participantResult.personalBestRun == null -> didNotStartPoints
            else -> positionToPoints[participantResult.position] ?: defaultPoints
        }
    }
}