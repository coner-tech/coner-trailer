package tech.coner.trailer.render.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.node.ObjectNode
import tech.coner.trailer.EventContext
import tech.coner.trailer.eventresults.TopTimesEventResults
import tech.coner.trailer.render.TopTimesEventResultsRenderer
import tech.coner.trailer.render.json.model.TopTimesEventResultsModel

class JsonTopTimesEventResultsRenderer(
    private val objectMapper: ObjectMapper
) : TopTimesEventResultsRenderer<String, ObjectNode> {

    private val writer: ObjectWriter = objectMapper.writerFor(TopTimesEventResultsModel::class.java)

    override fun render(eventContext: EventContext, results: TopTimesEventResults): String {
        val model = TopTimesEventResultsModel(eventContext.event, results)
        return writer.writeValueAsString(model)
    }

    override fun partial(eventContext: EventContext, results: TopTimesEventResults): ObjectNode {
        val model = TopTimesEventResultsModel(eventContext.event, results)
        return objectMapper.valueToTree(model)
    }
}