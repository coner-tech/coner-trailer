package tech.coner.trailer.render.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.IndividualEventResults
import tech.coner.trailer.render.IndividualEventResultsRenderer
import tech.coner.trailer.render.json.model.IndividualEventResultsModel

class JsonIndividualEventResultsRenderer(
    private val objectMapper: ObjectMapper
) : IndividualEventResultsRenderer<String, ObjectNode> {

    override fun render(event: Event, results: IndividualEventResults): String {
        val model = IndividualEventResultsModel(event, results)
        return objectMapper.writeValueAsString(model)
    }

    override fun partial(event: Event, results: IndividualEventResults): ObjectNode {
        val model = IndividualEventResultsModel(event, results)
        return objectMapper.valueToTree(model)
    }
}