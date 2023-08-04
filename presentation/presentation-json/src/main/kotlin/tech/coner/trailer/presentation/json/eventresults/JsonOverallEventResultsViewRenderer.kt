package tech.coner.trailer.presentation.json.eventresults

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import tech.coner.trailer.eventresults.OverallEventResults
import tech.coner.trailer.presentation.json.internal.model.OverallEventResultsModel

class JsonOverallEventResultsViewRenderer(
    objectMapper: ObjectMapper
) {

    private val writer: ObjectWriter = objectMapper.writerFor(OverallEventResultsModel::class.java)

    fun render(model: OverallEventResults): String {
        return writer.writeValueAsString(OverallEventResultsModel(model))
    }
}