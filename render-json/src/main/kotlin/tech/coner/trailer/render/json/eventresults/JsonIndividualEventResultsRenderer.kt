package tech.coner.trailer.render.json.eventresults

import com.fasterxml.jackson.databind.ObjectMapper
import tech.coner.trailer.eventresults.IndividualEventResults
import tech.coner.trailer.render.eventresults.IndividualEventResultsRenderer
import tech.coner.trailer.render.json.internal.model.IndividualEventResultsModel

class JsonIndividualEventResultsRenderer(
    private val objectMapper: ObjectMapper
) : IndividualEventResultsRenderer {

    override fun render(model: IndividualEventResults): String {
        return objectMapper.writeValueAsString(IndividualEventResultsModel(model))
    }
}