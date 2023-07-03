package tech.coner.trailer.render.json.eventresults

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import tech.coner.trailer.eventresults.ClassEventResults
import tech.coner.trailer.render.json.internal.model.ClazzEventResultsModel
import tech.coner.trailer.render.view.eventresults.EventResultsViewRenderer

class JsonClassEventResultsViewRenderer(
    objectMapper: ObjectMapper
) : EventResultsViewRenderer<ClassEventResults> {

    private val writer: ObjectWriter = objectMapper.writerFor(ClazzEventResultsModel::class.java)

    override fun render(model: ClassEventResults): String {
        return writer.writeValueAsString(ClazzEventResultsModel(model))
    }
}