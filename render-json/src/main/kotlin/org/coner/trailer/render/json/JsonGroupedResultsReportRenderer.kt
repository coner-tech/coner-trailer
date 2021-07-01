package org.coner.trailer.render.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.node.ObjectNode
import org.coner.trailer.Event
import org.coner.trailer.eventresults.GroupedResultsReport
import org.coner.trailer.render.ResultsReportRenderer
import org.coner.trailer.render.json.identifier.EventIdentifier
import org.coner.trailer.render.json.model.GroupedEventResultsModel

class JsonGroupedResultsReportRenderer(
    private val objectMapper: ObjectMapper
) : ResultsReportRenderer<GroupedResultsReport, String, ObjectNode> {

    private val writer: ObjectWriter = objectMapper.writerFor(GroupedEventResultsModel::class.java)

    override fun render(event: Event, report: GroupedResultsReport): String {
        val model = GroupedEventResultsModel(event, report)
        return writer.writeValueAsString(model)
    }

    override fun partial(event: Event, report: GroupedResultsReport): ObjectNode {
        val model = GroupedEventResultsModel(event, report)
        return objectMapper.valueToTree(model)
    }


}