package tech.coner.trailer.render.json.eventresults

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import tech.coner.trailer.eventresults.TopTimesEventResults
import tech.coner.trailer.render.json.internal.model.TopTimesEventResultsModel
import tech.coner.trailer.render.view.eventresults.EventResultsViewRenderer

class JsonTopTimesEventResultsViewRenderer(
    private val objectMapper: ObjectMapper
) : EventResultsViewRenderer<TopTimesEventResults> {

    private val writer: ObjectWriter = objectMapper.writerFor(TopTimesEventResultsModel::class.java)

    override fun render(model: TopTimesEventResults): String {
        return writer.writeValueAsString(TopTimesEventResultsModel(model))
    }
}