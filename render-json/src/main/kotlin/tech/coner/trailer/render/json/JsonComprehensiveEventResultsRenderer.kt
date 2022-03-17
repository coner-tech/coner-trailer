package tech.coner.trailer.render.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.ComprehensiveEventResults
import tech.coner.trailer.render.ComprehensiveEventResultsRenderer
import tech.coner.trailer.render.json.model.ComprehensiveEventResultsModel

class JsonComprehensiveEventResultsRenderer(
    private val objectMapper: ObjectMapper
) : ComprehensiveEventResultsRenderer<String, ObjectNode>  {

    override fun render(event: Event, results: ComprehensiveEventResults): String {
        val model = ComprehensiveEventResultsModel(event, results)
        return objectMapper.writeValueAsString(model)
    }

    override fun partial(event: Event, results: ComprehensiveEventResults): ObjectNode {
        val model = ComprehensiveEventResultsModel(event, results)
        return objectMapper.valueToTree(model)
    }
}