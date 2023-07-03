package tech.coner.trailer.render.json.eventresults

import com.fasterxml.jackson.databind.ObjectMapper
import tech.coner.trailer.eventresults.ComprehensiveEventResults
import tech.coner.trailer.render.json.internal.model.ComprehensiveEventResultsModel
import tech.coner.trailer.render.view.eventresults.EventResultsViewRenderer

class JsonComprehensiveEventResultsViewRenderer(
    private val objectMapper: ObjectMapper
) : EventResultsViewRenderer<ComprehensiveEventResults> {

    override fun render(model: ComprehensiveEventResults): String {
        return objectMapper.writeValueAsString(ComprehensiveEventResultsModel(model))
    }

}