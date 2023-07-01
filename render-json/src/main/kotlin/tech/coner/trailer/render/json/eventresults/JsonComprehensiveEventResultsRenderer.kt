package tech.coner.trailer.render.json.eventresults

import com.fasterxml.jackson.databind.ObjectMapper
import tech.coner.trailer.eventresults.ComprehensiveEventResults
import tech.coner.trailer.render.eventresults.ComprehensiveEventResultsRenderer
import tech.coner.trailer.render.json.internal.model.ComprehensiveEventResultsModel

class JsonComprehensiveEventResultsRenderer(
    private val objectMapper: ObjectMapper
) : ComprehensiveEventResultsRenderer {

    override fun render(model: ComprehensiveEventResults): String {
        return objectMapper.writeValueAsString(ComprehensiveEventResultsModel(model))
    }

}