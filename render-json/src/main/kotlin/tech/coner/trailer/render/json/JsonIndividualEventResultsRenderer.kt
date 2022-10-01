package tech.coner.trailer.render.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.IndividualEventResults
import tech.coner.trailer.render.IndividualEventResultsRenderer
import tech.coner.trailer.render.json.model.IndividualEventResultsModel

class JsonIndividualEventResultsRenderer(
    private val objectMapper: ObjectMapper
) : IndividualEventResultsRenderer<String, ObjectNode> {

    override fun render(eventContext: EventContext, results: IndividualEventResults): String {
        val model = IndividualEventResultsModel(
            event = eventContext.event,
            types = results.innerEventResultsTypes,
            results = results
        )
        return objectMapper.writeValueAsString(model)
    }

    override fun partial(eventContext: EventContext, results: IndividualEventResults): ObjectNode {
        val model = IndividualEventResultsModel(
            event = eventContext.event,
            types = results.innerEventResultsTypes,
            results = results
        )
        return objectMapper.valueToTree(model)
    }
}