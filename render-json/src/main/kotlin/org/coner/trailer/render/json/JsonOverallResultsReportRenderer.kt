package org.coner.trailer.render.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.node.ObjectNode
import org.coner.trailer.Event
import org.coner.trailer.eventresults.OverallResultsReport
import org.coner.trailer.render.ResultsReportRenderer
import org.coner.trailer.render.json.model.OverallEventResultsReportModel

class JsonOverallResultsReportRenderer(
    private val objectMapper: ObjectMapper
) : ResultsReportRenderer<OverallResultsReport, String, ObjectNode> {

    private val writer: ObjectWriter = objectMapper.writerFor(OverallEventResultsReportModel::class.java)

    override fun render(event: Event, report: OverallResultsReport): String {
        val model = OverallEventResultsReportModel(event, report)
        return writer.writeValueAsString(model)
    }

    override fun partial(event: Event, report: OverallResultsReport): ObjectNode {
        val model = OverallEventResultsReportModel(event, report)
        return objectMapper.valueToTree(model)
    }

}