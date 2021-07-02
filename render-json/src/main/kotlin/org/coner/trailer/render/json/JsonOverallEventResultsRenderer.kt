package org.coner.trailer.render.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.node.ObjectNode
import org.coner.trailer.Event
import org.coner.trailer.eventresults.OverallEventResults
import org.coner.trailer.render.EventResultsRenderer
import org.coner.trailer.render.json.model.OverallEventResultsModel

class JsonOverallEventResultsRenderer(
    private val objectMapper: ObjectMapper
) : EventResultsRenderer<OverallEventResults, String, ObjectNode> {

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