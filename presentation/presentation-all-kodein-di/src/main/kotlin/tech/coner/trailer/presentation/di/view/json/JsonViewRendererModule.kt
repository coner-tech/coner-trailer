package tech.coner.trailer.presentation.di.view.json

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import tech.coner.trailer.presentation.view.json.JsonView
import tech.coner.trailer.presentation.view.json.internal.JacksonJsonView
import tech.coner.trailer.presentation.model.Model

val jsonViewModule = DI.Module("tech.coner.trailer.presentation.view.json") {

    // Bindings for package: tech.coner.trailer.presentation.view.text

    bindSingleton<JsonView<Model>> {
        JacksonJsonView(
            objectMapper = jacksonMapperBuilder()
                .addModule(JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .build()
        )
    }
}