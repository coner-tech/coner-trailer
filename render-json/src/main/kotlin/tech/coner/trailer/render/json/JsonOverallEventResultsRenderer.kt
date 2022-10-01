package tech.coner.trailer.render.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.node.ObjectNode
import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.render.OverallEventResultsRenderer
import tech.coner.trailer.render.json.model.OverallEventResultsModel

class JsonOverallEventResultsRenderer(
    private val objectMapper: ObjectMapper
) : OverallEventResultsRenderer<String, ObjectNode> {

    private val writer: ObjectWriter = objectMapper.writerFor(OverallEventResultsModel::class.java)

    override fun render(eventContext: EventContext, results: OverallEventResults): String {
        val model = OverallEventResultsModel(eventContext.event, results)
        return writer.writeValueAsString(model)
    }

    override fun partial(eventContext: EventContext, results: OverallEventResults): ObjectNode {
        val model = OverallEventResultsModel(eventContext.event, results)
        return objectMapper.valueToTree(model)
    }

}