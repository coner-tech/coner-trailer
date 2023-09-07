package tech.coner.trailer.presentation.view.json.internal.model

import tech.coner.trailer.Class
import tech.coner.trailer.eventresults.ClassEventResults
import tech.coner.trailer.presentation.view.json.internal.identifier.EventIdentifier

class ClazzEventResultsModel(
    val event: EventIdentifier,
    val classes: Map<String, Class>,
    val results: ResultsModel
) {
    constructor(results: ClassEventResults) : this(
        event = EventIdentifier(results.eventContext.event),
        classes = results.eventContext.classes.associateBy(Class::abbreviation),
        results = ResultsModel(results),
    )

    class ResultsModel(
        val type: String,
        val runCount: Int,
        val clazzParticipantResults: Map<String, List<ParticipantResultModel>>,
    ) {
        constructor(results: ClassEventResults) : this(
            type = results.type.key,
            runCount = results.runCount,
            clazzParticipantResults = results.groupParticipantResults
                .map { (group, results) ->
                    group.abbreviation to results.map { ParticipantResultModel(it) }
                }
                .toMap()
        )
    }
}