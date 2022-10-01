package tech.coner.trailer.render.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.ComprehensiveEventResults
import tech.coner.trailer.render.ComprehensiveEventResultsRenderer
import tech.coner.trailer.render.json.model.ComprehensiveEventResultsModel

class JsonComprehensiveEventResultsRenderer(
    private val objectMapper: ObjectMapper
) : ComprehensiveEventResultsRenderer<String, ObjectNode>  {

    override fun render(eventContext: EventContext, results: ComprehensiveEventResults): String {
        val model = ComprehensiveEventResultsModel(eventContext.event, results)
        return objectMapper.writeValueAsString(model)
    }

    override fun partial(eventContext: EventContext, results: ComprehensiveEventResults): ObjectNode {
        val model = ComprehensiveEventResultsModel(eventContext.event, results)
        return objectMapper.valueToTree(model)
    }
}