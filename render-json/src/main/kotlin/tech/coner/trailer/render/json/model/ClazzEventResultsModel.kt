package tech.coner.trailer.render.json.model

import tech.coner.trailer.Class
import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.ClazzEventResults
import tech.coner.trailer.render.json.identifier.EventIdentifier

class ClazzEventResultsModel(
    val event: EventIdentifier,
    val classes: Map<String, Class>,
    val results: ResultsModel
) {
    constructor(eventContext: EventContext, results: ClazzEventResults) : this(
        event = EventIdentifier(eventContext.event),
        classes = eventContext.classes.associateBy(Class::abbreviation),
        results = ResultsModel(results),
    )

    class ResultsModel(
        val type: String,
        val runCount: Int,
        val clazzParticipantResults: Map<String, List<ParticipantResultModel>>,
    ) {
        constructor(results: ClazzEventResults) : this(
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