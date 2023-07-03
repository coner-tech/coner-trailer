package tech.coner.trailer.render.json.eventresults

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.render.json.internal.model.OverallEventResultsModel
import tech.coner.trailer.render.view.eventresults.EventResultsViewRenderer

class JsonOverallEventResultsViewRenderer(
    objectMapper: ObjectMapper
) : EventResultsViewRenderer<OverallEventResults> {

    private val writer: ObjectWriter = objectMapper.writerFor(OverallEventResultsModel::class.java)

    override fun render(model: OverallEventResults): String {
        return writer.writeValueAsString(OverallEventResultsModel(model))
    }
}