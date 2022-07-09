package tech.coner.trailer.render.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.node.ObjectNode
import tech.coner.trailer.Event
import tech.coner.trailer.eventresults.ClazzEventResults
import tech.coner.trailer.render.GroupEventResultsRenderer
import tech.coner.trailer.render.json.model.GroupedEventResultsModel

class JsonGroupEventResultsRenderer(
    private val objectMapper: ObjectMapper
) : GroupEventResultsRenderer<String, ObjectNode> {

    private val writer: ObjectWriter = objectMapper.writerFor(GroupedEventResultsModel::class.java)

    override fun render(event: Event, results: ClazzEventResults): String {
        val model = GroupedEventResultsModel(event, results)
        return writer.writeValueAsString(model)
    }

    override fun partial(event: Event, results: ClazzEventResults): ObjectNode {
        val model = GroupedEventResultsModel(event, results)
        return objectMapper.valueToTree(model)
    }


}