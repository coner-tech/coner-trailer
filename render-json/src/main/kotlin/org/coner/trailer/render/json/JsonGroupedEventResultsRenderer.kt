package org.coner.trailer.render.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.node.ObjectNode
import org.coner.trailer.Event
import org.coner.trailer.eventresults.GroupedEventResults
import org.coner.trailer.render.EventResultsRenderer
import org.coner.trailer.render.json.model.GroupedEventResultsModel

class JsonGroupedEventResultsRenderer(
    private val objectMapper: ObjectMapper
) : EventResultsRenderer<GroupedEventResults, String, ObjectNode> {

    private val writer: ObjectWriter = objectMapper.writerFor(GroupedEventResultsModel::class.java)

    override fun render(event: Event, results: GroupedEventResults): String {
        val model = GroupedEventResultsModel(event, results)
        return writer.writeValueAsString(model)
    }

    override fun partial(event: Event, results: GroupedEventResults): ObjectNode {
        val model = GroupedEventResultsModel(event, results)
        return objectMapper.valueToTree(model)
    }


}