package tech.coner.trailer.presentation.view.json.eventresults

import com.fasterxml.jackson.databind.ObjectMapper
import tech.coner.trailer.eventresults.IndividualEventResults
import tech.coner.trailer.presentation.view.json.internal.model.IndividualEventResultsModel

class JsonIndividualEventResultsViewRenderer(
    private val objectMapper: ObjectMapper
) {

    fun render(model: IndividualEventResults): String {
        return objectMapper.writeValueAsString(IndividualEventResultsModel(model))
    }
}