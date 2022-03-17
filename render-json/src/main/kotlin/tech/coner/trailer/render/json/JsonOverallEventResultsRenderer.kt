package tech.coner.trailer.render.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.node.ObjectNode
import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.render.OverallEventResultsRenderer
import tech.coner.trailer.render.json.model.OverallEventResultsModel

class JsonOverallEventResultsRenderer(
    private val objectMapper: ObjectMapper
) : OverallEventResultsRenderer<String, ObjectNode> {

    private val writer: ObjectWriter = objectMapper.writerFor(OverallEventResultsModel::class.java)

    override fun render(event: Event, results: OverallEventResults): String {
        val model = OverallEventResultsModel(event, results)
        return writer.writeValueAsString(model)
    }

    override fun partial(event: Event, results: OverallEventResults): ObjectNode {
        val model = OverallEventResultsModel(event, results)
        return objectMapper.valueToTree(model)
    }

}