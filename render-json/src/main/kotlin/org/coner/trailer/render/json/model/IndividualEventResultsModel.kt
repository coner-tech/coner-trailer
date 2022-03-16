package org.coner.trailer.render.json.model

import org.coner.trailer.Event
import org.coner.trailer.Participant
import org.coner.trailer.Time
import org.coner.trailer.eventresults.EventResultsType
import org.coner.trailer.eventresults.IndividualEventResults
import org.coner.trailer.eventresults.ParticipantResult
import org.coner.trailer.eventresults.Score
import org.coner.trailer.render.json.identifier.EventIdentifier
import org.coner.trailer.render.json.identifier.ParticipantIdentifier

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