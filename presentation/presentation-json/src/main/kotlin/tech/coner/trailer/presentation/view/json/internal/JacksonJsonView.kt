package tech.coner.trailer.presentation.view.json.internal

import com.fasterxml.jackson.databind.ObjectMapper
import tech.coner.trailer.presentation.view.json.JsonView
import tech.coner.trailer.presentation.model.Model

class JacksonJsonView(
    private val objectMapper: ObjectMapper
) : JsonView<Model> {

    override fun invoke(model: Model): String {
        return objectMapper.writeValueAsString(model)
    }
}