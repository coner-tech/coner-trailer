package tech.coner.trailer.render.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.node.ObjectNode
import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.ClazzEventResults
import tech.coner.trailer.render.ClazzEventResultsRenderer
import tech.coner.trailer.render.json.model.ClazzEventResultsModel

class JsonClazzEventResultsRenderer(
    private val objectMapper: ObjectMapper
) : ClazzEventResultsRenderer<String, ObjectNode> {

    private val writer: ObjectWriter = objectMapper.writerFor(ClazzEventResultsModel::class.java)

    override fun render(event: Event, results: ClazzEventResults): String {
        val model = ClazzEventResultsModel(event, results)
        return writer.writeValueAsString(model)
    }

    override fun partial(event: Event, results: ClazzEventResults): ObjectNode {
        val model = ClazzEventResultsModel(event, results)
        return objectMapper.valueToTree(model)
    }
}