package tech.coner.trailer.render.json.model

import tech.coner.trailer.Event
import tech.coner.trailer.Participant
import tech.coner.trailer.Time
import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.eventresults.IndividualEventResults
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.eventresults.Score
import tech.coner.trailer.render.json.identifier.EventIdentifier
import tech.coner.trailer.render.json.identifier.ParticipantIdentifier

class IndividualEventResultsModel(
    val event: EventIdentifier,
    val results: ResultsModel
) {

    constructor(event: Event, results: IndividualEventResults) : this(
        event = EventIdentifier(event),
        results = ResultsModel(results)
    )

    class ResultsModel(
        val type: String,
        val runCount: Int,
        val allByParticipant: List<IndividualParticipantResultModel>
    ) {
        constructor(
            results: IndividualEventResults
        ) : this(
            type = results.type.key,
            runCount = results.runCount,
            allByParticipant = results.allByParticipant.map { IndividualParticipantResultModel(it) }
        )
    }

    class IndividualParticipantResultModel(
        val participant: ParticipantIdentifier,
        val results: Map<String, ParticipantResultModel>
    ) {
        constructor(
            entry: Map.Entry<Participant, Map<EventResultsType, ParticipantResult>>
        ) : this(
            participant = ParticipantIdentifier(entry.key),
            results = entry.value
                .map { it.key.key to ParticipantResultModel(it.value) }
                .toMap()
        )
    }

    class ParticipantResultModel(
        val position: Int,
        val score: Score,
        val diffFirst: Time?,
        val diffPrevious: Time?,
        val allRuns: List<RunModel>,
        val scoredRuns: List<ResultRunModel>,
        val personalBestScoredRunIndex: Int?
    ) {
        constructor(participantResult: ParticipantResult) : this(
            position = participantResult.position,
            score = participantResult.score,
            diffFirst = participantResult.diffFirst,
            diffPrevious = participantResult.diffPrevious,
            allRuns = participantResult.allRuns.map { RunModel(it) },
            scoredRuns = participantResult.scoredRuns.map { ResultRunModel(it) },
            personalBestScoredRunIndex = participantResult.personalBestScoredRunIndex
        )
    }
}