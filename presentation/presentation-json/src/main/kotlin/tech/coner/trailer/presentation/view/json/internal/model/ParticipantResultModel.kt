package tech.coner.trailer.presentation.view.json.internal.model

import tech.coner.trailer.Time
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.presentation.view.json.internal.identifier.ParticipantIdentifier

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