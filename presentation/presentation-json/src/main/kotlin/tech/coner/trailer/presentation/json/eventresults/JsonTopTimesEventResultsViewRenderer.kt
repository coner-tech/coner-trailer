package tech.coner.trailer.presentation.json.eventresults

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import tech.coner.trailer.eventresults.TopTimesEventResults
import tech.coner.trailer.presentation.json.internal.model.TopTimesEventResultsModel

class JsonTopTimesEventResultsViewRenderer(
    private val objectMapper: ObjectMapper
) {

    private val writer: ObjectWriter = objectMapper.writerFor(TopTimesEventResultsModel::class.java)

    fun render(model: TopTimesEventResults): String {
        return writer.writeValueAsString(TopTimesEventResultsModel(model))
    }
}