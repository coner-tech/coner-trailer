package tech.coner.trailer.render.json.eventresults

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import tech.coner.trailer.eventresults.ClassEventResults
import tech.coner.trailer.render.eventresults.ClassEventResultsRenderer
import tech.coner.trailer.render.json.internal.model.ClazzEventResultsModel

class JsonClassEventResultsRenderer(
    objectMapper: ObjectMapper
) : ClassEventResultsRenderer {

    private val writer: ObjectWriter = objectMapper.writerFor(ClazzEventResultsModel::class.java)

    override fun render(model: ClassEventResults): String {
        return writer.writeValueAsString(ClazzEventResultsModel(model))
    }
}