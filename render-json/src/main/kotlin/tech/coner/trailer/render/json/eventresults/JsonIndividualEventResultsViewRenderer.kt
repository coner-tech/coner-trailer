package tech.coner.trailer.render.json.eventresults

import com.fasterxml.jackson.databind.ObjectMapper
import tech.coner.trailer.eventresults.IndividualEventResults
import tech.coner.trailer.render.json.internal.model.IndividualEventResultsModel
import tech.coner.trailer.render.view.eventresults.EventResultsViewRenderer

class JsonIndividualEventResultsViewRenderer(
    private val objectMapper: ObjectMapper
) : EventResultsViewRenderer<IndividualEventResults> {

    override fun render(model: IndividualEventResults): String {
        return objectMapper.writeValueAsString(IndividualEventResultsModel(model))
    }
}