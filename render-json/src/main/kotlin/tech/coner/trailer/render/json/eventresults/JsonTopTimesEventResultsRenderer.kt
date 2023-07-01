package tech.coner.trailer.render.json.eventresults

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import tech.coner.trailer.eventresults.TopTimesEventResults
import tech.coner.trailer.render.eventresults.TopTimesEventResultsRenderer
import tech.coner.trailer.render.json.internal.model.TopTimesEventResultsModel

class JsonTopTimesEventResultsRenderer(
    private val objectMapper: ObjectMapper
) : TopTimesEventResultsRenderer {

    private val writer: ObjectWriter = objectMapper.writerFor(TopTimesEventResultsModel::class.java)

    override fun render(model: TopTimesEventResults): String {
        return writer.writeValueAsString(TopTimesEventResultsModel(model))
    }
}