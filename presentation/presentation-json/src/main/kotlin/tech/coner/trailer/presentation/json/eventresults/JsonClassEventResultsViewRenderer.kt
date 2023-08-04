package tech.coner.trailer.presentation.json.eventresults

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import tech.coner.trailer.eventresults.ClassEventResults
import tech.coner.trailer.presentation.json.internal.model.ClazzEventResultsModel

class JsonClassEventResultsViewRenderer(
    objectMapper: ObjectMapper
) {

    private val writer: ObjectWriter = objectMapper.writerFor(ClazzEventResultsModel::class.java)

    fun render(model: ClassEventResults): String {
        return writer.writeValueAsString(ClazzEventResultsModel(model))
    }
}