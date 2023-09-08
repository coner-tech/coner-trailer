package tech.coner.trailer.presentation.view.json.internal.model

import tech.coner.trailer.Participant
import tech.coner.trailer.Time
import tech.coner.trailer.eventresults.EventResultsType
import tech.coner.trailer.eventresults.IndividualEventResults
import tech.coner.trailer.eventresults.ParticipantResult
import tech.coner.trailer.eventresults.Score
import tech.coner.trailer.presentation.view.json.internal.identifier.EventIdentifier
import tech.coner.trailer.presentation.view.json.internal.identifier.ParticipantIdentifier

class IndividualEventResultsModel(
    val event: EventIdentifier,
    val types: Map<String, EventResultsTypeModel>,
    val results: ResultsModel
) {

    constructor(results: IndividualEventResults) : this(
        event = EventIdentifier(results.eventContext.event),
        types = results.innerEventResultsTypes
            .map(::EventResultsTypeModel)
            .associateBy(EventResultsTypeModel::key),
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
            allByParticipant = results.resultsByIndividual.map { IndividualParticipantResultModel(it) }
        )
    }

    class IndividualParticipantResultModel(
        val participant: ParticipantIdentifier,
        val results: Map<String, ParticipantResultModel?>
    ) {
        constructor(
            pair: Pair<Participant, Map<EventResultsType, ParticipantResult?>>
        ) : this(
            participant = ParticipantIdentifier(pair.first),
            results = pair.second
                .map { it.key.key to it.value?.let(IndividualEventResultsModel::ParticipantResultModel) }
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