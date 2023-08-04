package tech.coner.trailer.presentation.json.eventresults

import com.fasterxml.jackson.databind.ObjectMapper
import tech.coner.trailer.eventresults.ComprehensiveEventResults
import tech.coner.trailer.presentation.json.internal.model.ComprehensiveEventResultsModel

class JsonComprehensiveEventResultsViewRenderer(
    private val objectMapper: ObjectMapper
) {

    fun render(model: ComprehensiveEventResults): String {
        return objectMapper.writeValueAsString(ComprehensiveEventResultsModel(model))
    }

}