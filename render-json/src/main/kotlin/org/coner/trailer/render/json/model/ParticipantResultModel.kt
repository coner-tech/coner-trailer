package org.coner.trailer.render.json.model

import org.coner.trailer.Time
import org.coner.trailer.eventresults.ParticipantResult
import org.coner.trailer.eventresults.Score
import org.coner.trailer.render.json.identifier.ParticipantIdentifier

class ParticipantResultModel(
    val position: Int,
    val score: ScoreModel,
    val participant: ParticipantIdentifier,
    val diffFirst: Time?,
    val diffPrevious: Time?,
    val scoredRuns: List<ResultRunModel>,
    val personalBestScoredRunIndex: Int?
) {
    constructor(participantResult: ParticipantResult) : this(
        position = participantResult.position,
        score = ScoreModel(participantResult.score),
        participant = ParticipantIdentifier(participantResult.participant),
        diffFirst = participantResult.diffFirst,
        diffPrevious = participantResult.diffPrevious,
        scoredRuns = participantResult.scoredRuns.map { ResultRunModel(it) },
        personalBestScoredRunIndex = participantResult.personalBestScoredRunIndex
    )
}